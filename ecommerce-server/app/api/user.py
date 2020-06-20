# coding=utf-8
import logging

import flask_restplus
from flask import request

import app.api.schema.request.user
import app.api.schema.response.user
from app import services
from app.extensions import Namespace

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

ns = Namespace('users', description='User operations')

_user_res = ns.model('user_res', app.api.response.user.user_res)


@ns.route('/get_status', methods=['GET'])
class UserStatus(flask_restplus.Resource):
    @ns.marshal_with(_user_res)
    def get(self):
        "fetch user status by checking jwt token"
        return services.user.fetch_user_status()


_login_req = ns.model('login_req', app.api.request.user.login_req)


@ns.marshal_with(_user_res)
@ns.route('/login', methods=['POST'])
class Login(flask_restplus.Resource):
    @ns.expect(_login_req, validate=True)
    def post(self):
        "check username and password and give jwt token to user"
        data = request.args or request.json
        resp = services.user.login(**data)
        return resp


@ns.route('/logout', methods=['POST'])
class Logout(flask_restplus.Resource):
    def post(self):
        "remove jwt token from httponly cookies"
        resp = services.user.logout()
        return resp
