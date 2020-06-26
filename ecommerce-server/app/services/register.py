# coding=utf-8
import logging

from config import TOKEN_UPTIME, SERVER_BASE_URL
from app.extensions.custom_exception import RegisterBeforeException, UserExistsException
from app.helpers import encode_token, string_utils
from app.helpers import validate_register, hash_password
from app.models import Register
from app.repositories.mysql import register as repo
from app.services import mail_service, user as user_service
from app.templates import email_template

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def create_new_register(email, fullname, password, **kwargs) -> Register:
    phone_number = string_utils.normalize_phone_number(kwargs.get('phone_number'))
    validate_register(email, phone_number, fullname, password)

    if phone_number:
        existed_user = user_service.find_one_by_email_or_phone_number_ignore_case(email, phone_number)
    else:
        existed_user = user_service.find_one_by_email(email)

    if existed_user: raise UserExistsException()

    if phone_number:
        existed_pending_register = repo.find_one_by_email_or_phone_number(email, phone_number)
    else:
        existed_pending_register = repo.find_one_by_email(email)

    if existed_pending_register: raise RegisterBeforeException()

    return repo.create_new_register(
        email=email,
        fullname=fullname,
        phone_number=phone_number,
        password=hash_password(password),
        gender=kwargs.get('gender') or 1,
        address=kwargs.get('address') or ''
    )


def send_confirm_email(email, fullname, **kwargs):
    confirm_token = encode_token(email, int(TOKEN_UPTIME))
    active_link = SERVER_BASE_URL + 'register/confirm_email/' + confirm_token
    msg_html = email_template.gen_confirm_email_body_template(fullname, email, active_link)
    mail_service.send_email("uShop's email confirmation", email, msg_html)


# ======================================================================================================================
def delete_one_by_email(email: str):
    repo.delete_one_by_email(email)


def find_one_by_email(email: str) -> Register:
    return repo.find_one_by_email(email)


def find_one_by_phone_number(phone_number: str) -> Register:
    return repo.find_one_by_phone_number(phone_number)
