# coding=utf-8
import logging
from flask_restplus import fields

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


register_user_req = {
    'email': fields.String(required=True, description='user email address'),
    'phone_number': fields.String(required=False, fields="phone_number", description='phone number of user'),
    'fullname': fields.String(required=True, description='fullname of user'),
    'password': fields.String(required=True, description='raw password of user'),
    'address': fields.String(required=False, description='Adress of user'),
    'gender': fields.Integer(required=False, description='Gender of user. 1 -> male')
}
login_req = {
    'username': fields.String(required=True, description='email or phone number'),
    'password': fields.String(required=True, description='password'),
}
login_google_req = {
    'token': fields.String(required=True, description='Google login token'),
}
change_password_req = {
    'current_password': fields.String(required=True, description='current password'),
    'new_password': fields.String(required=True, description='new password')
}
change_profile_req = {
    'avatar_url': fields.String(required=False, description='new url of user data'),
    'address': fields.String(required=False, description='New address of user'),
    'gender': fields.Integer(required=False, description='New gender of user')
}
