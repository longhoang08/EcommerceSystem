# coding=utf-8
import logging

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

from ecommerce_server.extensions.exceptions import HTTPException
from ecommerce_server.commons import exception_message


class WrongPasswordException(HTTPException):
    def __init__(self, message=exception_message.WRONG_PASSWORD, errors=None):
        super().__init__(code=401, message=message, errors=errors, custom_code='wrong_pass')


class WrongCurrentPasswordException(HTTPException):
    def __init__(self, message=exception_message.WRONG_CUR_PASSWORD, errors=None):
        super().__init__(code=401, message=message, errors=errors, custom_code='wrong_cur_pass')


class PasswordDiffException(HTTPException):
    def __init__(self, message=exception_message.PASSWORD_DIFF, errors=None):
        super().__init__(code=401, message=message, errors=errors, custom_code='pass_diff')


class BlockingException(HTTPException):
    def __init__(self, message=exception_message.BLOCKING, errors=None):
        super().__init__(code=403, message=message, errors=errors, custom_code='blocking')


class UserBlockedException(HTTPException):
    def __init__(self, message=exception_message.BLOCKED, errors=None):
        super().__init__(code=403, message=message, errors=errors, custom_code='blocked')


class PermissionException(HTTPException):
    def __init__(self, message=exception_message.PERMISSION, errors=None):
        super().__init__(code=403, message=message, errors=errors, custom_code='permission')


class NeedLoggedInException(HTTPException):
    def __init__(self, message=exception_message.PLEASE_LOGIN, errors=None):
        super().__init__(code=401, message=message, errors=errors, custom_code='please_login')


class InvalidLoginTokenException(HTTPException):
    def __init__(self, message=exception_message.INVALID_LOGIN_TOKEN, errors=None):
        super().__init__(code=401, message=message, errors=errors, custom_code='invalid_login_token')


class InvalidTokenException(HTTPException):
    def __init__(self, message=exception_message.INVALID_TOKEN, errors=None):
        super().__init__(code=401, message=message, errors=errors, custom_code='invalid_token')


class EncodeErrorException(HTTPException):
    def __init__(self, message=exception_message.ENCODE_ERR, errors=None):
        super().__init__(code=401, message=message, errors=errors, custom_code='encode_err')


class CantSendEmailException(HTTPException):
    def __init__(self, message=exception_message.CANT_SEND_EMAIL, errors=None):
        super().__init__(code=502, message=message, errors=errors, custom_code='mail_server_err')


class UserNotFoundException(HTTPException):
    def __init__(self, message=exception_message.USER_NOT_FOUND, errors=None):
        super().__init__(code=400, message=message, errors=errors, custom_code='user_not_found')


class UsernameNotMatchEmailException(HTTPException):
    def __init__(self, message=exception_message.USERNAME_NOT_MATCH_EMAIL, errors=None):
        super().__init__(code=401, message=message, errors=errors, custom_code='username_not_match_email')


class RegisterBeforeException(HTTPException):
    def __init__(self, message=exception_message.REGISTED_BEFORE, errors=None):
        super().__init__(code=409, message=message, errors=errors, custom_code='registed_before')


class UserExistsException(HTTPException):
    def __init__(self, message=exception_message.USER_EXIST, errors=None):
        super().__init__(code=409, message=message, errors=errors, custom_code='user_exists')


class NotRegisterdException(HTTPException):
    def __init__(self, message=exception_message.REGISTED, errors=None):
        super().__init__(code=401, message=message, errors=errors, custom_code='not_registerd')


class MustConfirmEmailException(HTTPException):
    def __init__(self, message=exception_message.MUST_CONFIRM_EMAIL, errors=None):
        super().__init__(code=401, message=message, errors=errors, custom_code='must_confirm')


class NotInPendingException(HTTPException):
    def __init__(self, message=exception_message.NOT_IN_PENDING, errors=None):
        super().__init__(code=401, message=message, errors=errors, custom_code='not_pending')


class InvalidGoogleTokenException(HTTPException):
    def __init__(self, message=exception_message.INVALID_GOOGLE_TOKEN, errors=None):
        super().__init__(code=401, message=message, errors=errors, custom_code='invalid_google_token')
