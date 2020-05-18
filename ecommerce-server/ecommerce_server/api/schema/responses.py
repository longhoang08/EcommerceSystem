# coding=utf-8
from flask_restplus import fields

from ecommerce_server.api import api

user_res = {
    'email': fields.String(description='user email address'),
    'username': fields.String(description='user username'),
    'fullname': fields.String(description='fullname of user'),
    'avatar_url': fields.String(description='avatar url of user'),
}

pending_register_res = {
    'username': fields.String(description="Username"),
    'email': fields.String(description="Email"),
    'fullname': fields.String(description="User full name"),
}
