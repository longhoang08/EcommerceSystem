# coding=utf-8
import logging

from flask import jsonify
from flask_jwt_extended import create_access_token, set_access_cookies, unset_jwt_cookies

from ecommerce_server.helpers import validator, get_max_age, get_current_timestamp, verify_password
from ecommerce_server.models import User
from ecommerce_server.repositories import user as repo
from ecommerce_server.services import register as register_service, password as password_service

from ecommerce_server.extensions.custom_exception import MustConfirmEmailException, UserNotFoundException, \
    UserExistsException, NotInPendingException, NeedLoggedInException, PermissionException, UserBlockedException, \
    WrongCurrentPasswordException

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


# create user from a register after validate
def create_user(email: str, phone_number: str, fullname: str, password: str, **kwargs) -> User:
    existed_user = repo.find_one_by_email_or_phone_number(email, phone_number)
    if existed_user:
        raise UserExistsException()
    user = repo.create_new_user(
        email=email,
        fullname=fullname,
        phone_number=phone_number,
        password=password,
        **kwargs
    )
    return user


def create_new_user_from_register(email):
    register = register_service.find_one_by_email(email)
    if not register:
        raise NotInPendingException()
    return create_user(email, register.phone_number, register.fullname, register.password)


# create new user, delete pending register request and save password to historic password
def confirm_user_by_email(email: str) -> User:
    new_user = create_new_user_from_register(email)
    register_service.delete_one_by_email(email)
    password_service.add_new_hashed_password(new_user.id, new_user.password)
    return new_user


def fetch_user_status_by_email(email):
    from ecommerce_server.constant.user import Constant_user
    user = repo.find_one_by_email(email)
    return Constant_user.none_user if not user else user


def login(username, password, **data):
    user = check_username_and_password(username, password)
    resp = jsonify(user.to_display_dict())
    access_token = create_access_token(identity=user.email)
    set_access_cookies(resp, access_token, max_age=get_max_age())
    return resp


def logout():
    resp = jsonify({'logout': True})
    unset_jwt_cookies(resp)
    return resp


def change_password(email: str, current_password: str, new_password: str, **kwargs) -> User:
    user = repo.find_one_by_email(email)
    if not user: raise UserNotFoundException()
    if not verify_password(user.password, current_password): raise WrongCurrentPasswordException()
    return create_or_update_password(user, new_password)


# ======================================================================================================================
def find_one_by_user_id(user_id: int) -> User:
    return repo.find_one_by_user_id(user_id)


def create_or_update_password(user: User, hashed_password: str) -> User:
    password_service.add_new_hashed_password(user.id, hashed_password)
    user.password = password_service
    repo.save(user)
    return user


def handle_in_active(user) -> User:
    now = get_current_timestamp()
    if (now < user.un_block_at.timestamp()): raise UserBlockedException()
    user.is_active = True  # Unblock user
    return repo.save(user)


def check_username_and_password(email_or_phone_number: str, password: str):
    email, phone_number = validator.validate_login_request(email_or_phone_number, password)

    pending_register = register_service.find_one_by_email(email) if email else \
        register_service.find_one_by_phone_number(phone_number)

    if (pending_register): raise MustConfirmEmailException()

    user = repo.find_one_by_email(email) if email else repo.find_one_by_phone_number(phone_number)
    if not user: raise UserNotFoundException()
    if not user.is_active:
        handle_in_active(user)
    return user


def check_permission(user_email):
    from .token import check_jwt_token
    jwt_email = check_jwt_token()
    if (jwt_email == None):
        raise NeedLoggedInException()
    if (user_email != jwt_email):
        raise PermissionException()


def find_one_by_email_or_phone_number_ignore_case(email: str, phone_number: str) -> User:
    return repo.find_one_by_email_or_phone_number(email, phone_number)