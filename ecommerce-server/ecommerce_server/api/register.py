# coding=utf-8
import logging

import flask_restplus
from flask import request

from ecommerce_server import services
from ecommerce_server.extensions import Namespace
from ecommerce_server.helpers import decode_token
from ecommerce_server.services.pending_register import send_confirm_email
# from file_management.api import requests, responses
from . import requests, responses

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

ns = Namespace('register', description='Register operations')

_register_req = ns.model('register_req', requests.register_user_req, )
_register_res = ns.model('register_res', responses.register_res)


@ns.route('/', methods=['GET', 'POST'])
class Registers(flask_restplus.Resource):
    @ns.expect(_register_req, validate=True)
    @ns.marshal_with(_register_res)
    def post(self):
        data = request.args or request.json
        pending_register = services.pending_register.create_pending_register(**data)
        send_confirm_email(**data)  # Todo: Move send email to job queue to handle mail server error
        return pending_register


@ns.route('/confirm_email/<token>', methods=['GET'])
class Confirm_email(flask_restplus.Resource):

    def get(self, token: str):
        "checking jwt token in param and add new user to user table"
        email = decode_token(token)
        user = services.user.confirm_user_by_email(email)
        return user.to_display_dict()
