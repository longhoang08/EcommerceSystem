# coding=utf-8
import logging

import flask_restplus
from flask import request

from ecommerce_server import services
from ecommerce_server.extensions import Namespace
from ecommerce_server.extensions.custom_exception import InvalidLoginTokenException
from . import responses, requests

__author__ = 'longhb'
_logger = logging.getLogger(__name__)

ns = Namespace('users', description='User operations')

_user_res = ns.model('user_res', responses.user_res)


@ns.route('/get_status', methods=['GET'])
class UserStatus(flask_restplus.Resource):
    @ns.marshal_with(_user_res)

    def get(self):
        "fetch user status by checking jwt token"
        return services.user.fetch_user_status()


_login_req = ns.model('login_req', requests.login_req)


@ns.marshal_with(_user_res)
@ns.route('/login', methods=['GET', 'POST'])
class Login(flask_restplus.Resource):
    @ns.expect(_login_req, validate=True)
    def post(self):
        "check username and password and give jwt token to user"
        data = request.args or request.json
        resp = services.user.login(**data)
        return resp


@ns.route('/logout', methods=['GET', 'POST'])
class Logout(flask_restplus.Resource):
    def post(self):
        "remove jwt token from httponly cookies"
        resp = services.user.logout()
        return resp
