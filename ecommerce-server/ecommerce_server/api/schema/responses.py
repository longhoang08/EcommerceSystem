# coding=utf-8
from flask_restplus import fields

user_res = {
    'email': fields.String(description="User's email address"),
    'fullname': fields.String(description='Fullname of user'),
    'avatar_url': fields.String(description='Avatar url of user'),
}

register_res = {
    'email': fields.String(description="Email"),
    'fullname': fields.String(description="User full name"),
}
