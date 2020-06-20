# coding=utf-8
import logging

from flask_mail import Mail

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

from . import user
from . import register
from . import mail_service
from . import password
from . import product
from . import file
from . import seller
my_mail = Mail()


def init_app(app):
    my_mail.init_app(app)
