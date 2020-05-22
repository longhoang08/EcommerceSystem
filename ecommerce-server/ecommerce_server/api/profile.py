# coding=utf-8
import logging

import flask_restplus
from flask import request

from ecommerce_server import services
from ecommerce_server.extensions import Namespace
from . import requests, responses

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

ns = Namespace('profile', description='Profile operations')

_change_req = ns.model('change_password_req', requests.change_password_req)
_change_res = ns.model('change_password_res', responses.user_res)


@ns.route('/change_password', methods=['POST'])
class Change_password(flask_restplus.Resource):
    @ns.expect(_change_req, validate=True)
    @ns.marshal_with(_change_res)
    def post(self):
        "validate user by current password and jwt token and set new password"
        data = request.args or request.json
        return services.user.change_password(**data)
