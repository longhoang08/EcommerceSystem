# coding=utf-8
from flask_restplus import fields

register_user_req = {
    'email': fields.String(required=True, description='user email address'),
    'phone_number': fields.String(required=False, fields="phone_number", description='phone number of user'),
    'fullname': fields.String(required=False, description='fullname of user'),
    'password': fields.String(required=True, description='raw password of user'),
}

login_req = {
    'username': fields.String(required=True, description='email or phone number'),
    'password': fields.String(required=True, description='password'),
}

login_google_req = {
    'token': fields.String(required=True, description='Google login token'),
}

change_password_req = {
    'email': fields.String(required=True, description='email'),
    'current_password': fields.String(required=True, description='current password'),
    'new_password': fields.String(required=True, description='new password')
}
