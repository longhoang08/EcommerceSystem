# coding=utf-8
import logging

from ecommerce_server import repositories
from ecommerce_server.constant import message
from ecommerce_server.extensions.custom_exception import BlockingException, WrongPasswordException

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def handle_wrong_password(user):
    id = user.id
    num_of_wrong_passwords = repositories.wrong_password.number_of_wrong_password_by_user_id(id)
    repositories.wrong_password.save_wrong_password_to_redis(user_id=id)
    if (num_of_wrong_passwords >= 4):
        repositories.user.block_user(user)
        raise BlockingException(message.BLOCKING)
    raise WrongPasswordException(message.WRONG_PASSWORD)
