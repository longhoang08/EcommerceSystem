# coding=utf-8
import logging
import os

from ecommerce_server import repositories as repo
from ecommerce_server.extensions.custom_exception import RegisterBeforeException, UserExistsException
from ecommerce_server.extensions.exceptions import BadRequestException
from ecommerce_server.helpers import encode_token
from ecommerce_server.helpers import validate_register, hash_password
from ecommerce_server.services import mail_service
from ecommerce_server.templates import email_template

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def create_pending_register(email, fullname, phone_number, password, **kwargs):
    if not validate_register(email, fullname, password):
        raise BadRequestException("Invalid user data specified!")

    existed_user = repo.user.find_one_by_email_or_phone_number_ignore_case(
        email, phone_number)

    if existed_user:
        raise UserExistsException()

    existed_pending_register = repo.pending_register.find_one_by_email_or_phone_number(email, phone_number)
    if existed_pending_register:
        raise RegisterBeforeException()

    pending_register = repo.pending_register.save_pending_register_to_database(
        email=email,
        fullname=fullname,
        phone_number=phone_number,
        password=hash_password(password)
    )
    return pending_register


def send_confirm_email(email, fullname, **kwargs):
    confirm_token = encode_token(email, int(os.environ['TOKEN_UPTIME']))
    active_link = os.environ['SERVER_BASE_URL'] + 'register/confirm_email/' + confirm_token
    msg_html = email_template.gen_confirm_email_body_template(fullname, email, active_link)
    mail_service.send_email("uShop's email confirmation", email, msg_html)
