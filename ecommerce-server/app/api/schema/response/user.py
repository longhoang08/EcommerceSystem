# coding=utf-8

import logging
from flask_restplus import fields

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

user_res = {
    'email': fields.String(description="User's email address"),
    'fullname': fields.String(description='Fullname of user'),
    'avatar_url': fields.String(description='Avatar url of user'),
    'address': fields.String(description='Adress of user'),
    'gender': fields.String(description='Gender of user'),
}
register_res = {
    'email': fields.String(description="Email"),
    'fullname': fields.String(description="User full name"),
}
