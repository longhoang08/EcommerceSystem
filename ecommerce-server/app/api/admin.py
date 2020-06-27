# coding=utf-8
import logging

import flask_restplus
from flask import request

import app.api.schema.request.user
import app.api.schema.response.user
from app import services
from app.extensions import Namespace
from app.helpers import decode_token
from app.services.register import send_confirm_email

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

ns = Namespace('admin', description='admin operations')

_get_seller_pending_req = ns.model('seller_pending_req', app.api.request.admin.get_pending_seller_req)


@ns.route('/pending', methods=['POST'])
class RegisterNewSeller(flask_restplus.Resource):
    @ns.expect(_get_seller_pending_req, validate=True)
    def post(self):
        data = request.args or request.json
        return services.admin.get_pending_seller(data)
