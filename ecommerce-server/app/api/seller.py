# coding=utf-8
import logging

import flask_restplus
from flask import request

from app import services
from app.api.schema import requests, responses
from app.extensions import Namespace

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

ns = Namespace('seller', description='Seller operations')

_seller_register_req = ns.model('seller_register_req', requests.seller_register_req)
_seller_register_res = ns.model('seller_register_res', responses.seller_res)


@ns.route('/register', methods=['POST'])
class RegisterNewSeller(flask_restplus.Resource):
    @ns.expect(_seller_register_req, validate=True)
    @ns.marshal_with(_seller_register_res)
    def post(self):
        data = request.args or request.json
        return services.seller.register_to_be_seller(**data).to_dict()
