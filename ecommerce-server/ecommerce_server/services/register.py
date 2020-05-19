# coding=utf-8
import logging

from config import TOKEN_UPTIME, SERVER_BASE_URL
from ecommerce_server.extensions.custom_exception import RegisterBeforeException, UserExistsException
from ecommerce_server.helpers import encode_token, string_utils
from ecommerce_server.helpers import validate_register, hash_password
from ecommerce_server.models import Register
from ecommerce_server.repositories import register as repo
from ecommerce_server.services import mail_service, user as user_service
from ecommerce_server.templates import email_template

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def create_new_register(email, fullname, phone_number, password, **kwargs) -> Register:
    phone_number = string_utils.normalize_phone_number(phone_number)
    validate_register(email, phone_number, fullname, password)

    existed_user = user_service.find_one_by_email_or_phone_number_ignore_case(email, phone_number)
    if existed_user: raise UserExistsException()

    existed_pending_register = repo.find_one_by_email_or_phone_number(email, phone_number)
    if existed_pending_register: raise RegisterBeforeException()

    return repo.create_new_register(
        email=email,
        fullname=fullname,
        phone_number=phone_number,
        password=hash_password(password)
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
