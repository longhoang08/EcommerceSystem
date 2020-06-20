from typing import Tuple

import email_validator

from app.commons.exception_message import PHONE_NUMBER_RULE, PASSWORD_RULE, FULLNAME_RULE, EMAIL_RULE
from app.helpers import string_utils


def validate_email(email: str) -> bool:
    try:
        email_validator.validate_email(email)  # validate and get info
    except email_validator.EmailNotValidError:
        return False
    return True


def validate_phone_number(phone_number) -> bool:
    if not phone_number: return True  # Allow phone number to be null or empty
    return len(phone_number) == 10 and phone_number[0] == '0'


def validate_password(password: str) -> bool:
    return len(password) >= 8 and len(password) <= 100


def validate_fullname(fullname: str) -> bool:
    return len(fullname) >= 4 and len(fullname) <= 100 and fullname.replace(' ', '').isalpha()


def validate_register(email: str, phone_number: str, fullname: str, password: str):
    from app import BadRequestException

    if not validate_email(email): raise BadRequestException(EMAIL_RULE)
    if not validate_fullname(fullname): raise BadRequestException(FULLNAME_RULE)
    if not validate_password(password): raise BadRequestException(PASSWORD_RULE)
    if not validate_phone_number(phone_number): raise BadRequestException(PHONE_NUMBER_RULE)


def validate_login_request(email_or_phone_number: str, password: str) -> Tuple[str, str]:
    from app import BadRequestException

    phone_number = None
    email = None
    if not validate_email(email_or_phone_number):
        phone_number = string_utils.normalize_phone_number(email_or_phone_number)
        if not validate_phone_number(phone_number):
            raise BadRequestException("Not a valid email or phone number")
    else:
        email = email_or_phone_number
    if not validate_password(password):
        raise BadRequestException("Invalid password format")
    return email, phone_number
