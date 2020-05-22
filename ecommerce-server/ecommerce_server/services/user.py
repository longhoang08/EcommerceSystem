# coding=utf-8
import logging

from flask import jsonify
from flask_jwt_extended import create_access_token, unset_jwt_cookies

from ecommerce_server.commons.decorators import login_required
from ecommerce_server.extensions.custom_exception import MustConfirmEmailException, UserNotFoundException, \
    UserExistsException, NotInPendingException, UserBlockedException, \
    WrongCurrentPasswordException, WrongPasswordException
from ecommerce_server.helpers import validator, get_current_timestamp, verify_password
from ecommerce_server.models import User, UserRole
from ecommerce_server.repositories import user as repo
from ecommerce_server.services import register as register_service, password as password_service

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


@login_required
def fetch_user_status(**kwargs):
    email = kwargs.get('email')
    user = repo.find_one_by_email(email)
    return user


def login(username, password):
    user = check_username_and_password(username, password)
    access_token = create_access_token(identity=user.email)
    return {"access_token": access_token}


def logout():
    # Todo: remove token from redis
    resp = jsonify({'logout': True})
    unset_jwt_cookies(resp)
    return resp


@login_required
def change_password(current_password: str, new_password: str, **kwargs) -> User:
    email = kwargs.get('email')
    user = repo.find_one_by_email(email)
    if not user: raise UserNotFoundException()
    if not verify_password(user.password, current_password): raise WrongCurrentPasswordException()
    return create_or_update_password(user, new_password)


@login_required
def change_profile(**kwargs) -> User:
    email = kwargs.get('email')
    user = repo.find_one_by_email(email)
    if not user: raise UserNotFoundException()
    return update_profile(user)


# ======================================================================================================================
def find_one_by_user_id(user_id: int) -> User:
    return repo.find_one_by_user_id(user_id)


def find_one_by_email(email: str) -> User:
    return repo.find_one_by_email(email)


def find_one_by_email_else_throw(email: str) -> User:
    user = repo.find_one_by_email(email)
    if not user: raise UserNotFoundException()
    return user


def create_or_update_password(user: User, password: str) -> User:
    hashed_password = password_service.add_new_hashed_password(user.id, password)
    user.password = hashed_password
    repo.save(user)
    return user


def update_profile(user: User, **kwargs) -> User:
    avatar_url = kwargs.get('avatar_url')
    if avatar_url:
        user.avatar_url = avatar_url
    return repo.save(user)


def handle_in_active(user) -> User:
    now = get_current_timestamp()
    if (now < user.un_block_at.timestamp()): raise UserBlockedException()
    user.is_active = True  # Unblock user
    return repo.save(user)


def check_username_and_password(email_or_phone_number: str, password: str):
    email, phone_number = validator.validate_login_request(email_or_phone_number, password)

    pending_register = register_service.find_one_by_email(email) if email else \
        register_service.find_one_by_phone_number(phone_number)

    if pending_register: raise MustConfirmEmailException()

    user = repo.find_one_by_email(email) if email else repo.find_one_by_phone_number(phone_number)

    if not user: raise UserNotFoundException()

    if not user.is_active: handle_in_active(user)

    if not verify_password(user.password, password): raise WrongPasswordException()

    return user


def find_one_by_email_or_phone_number_ignore_case(email: str, phone_number: str) -> User:
    return repo.find_one_by_email_or_phone_number(email, phone_number)
