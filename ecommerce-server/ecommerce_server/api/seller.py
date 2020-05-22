# coding=utf-8
import logging

import flask_restplus
import flask_restplus as _fr
from flask import request

from ecommerce_server import services
from ecommerce_server.api.schema import requests
from ecommerce_server.extensions import Namespace

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

ns = Namespace('Seller', description='Seller operations')

_seller_register_req = ns.model('seller_register_req', requests.seller_register_req)


@ns.route('/register', methods=['POST'])
class ChangePassword(flask_restplus.Resource):
    @ns.expect(_seller_register_req, validate=True)
    def post(self):
        "validate user by current password and jwt token and set new password"
        data = request.args or request.json
        return services.seller.register_to_be_seller(**data)
