# coding=utf-8
import logging

import flask_restplus
from flask import request

from app import services
from app.extensions import Namespace
from app.helpers import decode_token
from app.services.register import send_confirm_email
# from file_management.api import requests, responses
from . import requests, responses

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

ns = Namespace('register', description='Register operations')

_register_req = ns.model('register_req', requests.register_user_req)
_register_res = ns.model('register_res', responses.register_res)


@ns.route('/', methods=['POST'])
class Registers(flask_restplus.Resource):
    @ns.expect(_register_req, validate=True)
    @ns.marshal_with(_register_res)
    def post(self):
        data = request.args or request.json
        pending_register = services.register.create_new_register(**data)
        send_confirm_email(**data)  # Todo: Move send email to job queue to handle mail server error
        return pending_register


@ns.route('/confirm_email/<token>', methods=['GET'])
class ConfirmEmail(flask_restplus.Resource):
    def get(self, token: str):
        "checking jwt token in param and add new user to user table"
        email = decode_token(token)
        user = services.user.confirm_user_by_email(email)
        return user.to_display_dict()