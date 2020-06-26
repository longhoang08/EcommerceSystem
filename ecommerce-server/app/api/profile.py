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

ns = Namespace('profile', description='Profile operations')

_change_password_req = ns.model('change_password_req', app.api.request.user.change_password_req)
_user_res = ns.model('user_res', app.api.response.user.user_res)


@ns.route('/change_password', methods=['POST'])
class ChangePassword(flask_restplus.Resource):
    @ns.expect(_change_password_req, validate=True)
    @ns.marshal_with(_user_res)
    def post(self):
        "validate user by current password and jwt token and set new password"
        data = request.args or request.json
        return services.user.change_password(**data).to_display_dict()


_change_profile_req = ns.model('change_profile_req', app.api.schema.request.user.change_profile_req)


@ns.route('/update_profile', methods=['POST'])
class UpdateProfile(flask_restplus.Resource):
    @ns.expect(_change_profile_req, validate=True)
    @ns.marshal_with(_user_res)
    def post(self):
        "validate user by current password and jwt token and set new password"
        data = request.args or request.json
        return services.user.change_profile(**data).to_display_dict()
