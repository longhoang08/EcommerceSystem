# coding=utf-8
import logging

import flask_restplus
from flask import request

import app.api.schema.request.product
from app.extensions import Namespace
from app.helpers import decode_token
from app.services import order

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

ns = Namespace('order', description='Order management operations')

_order_details = ns.model('order_details', app.api.request.order.order_details_req)


@ns.route('/details', methods=['POST'])
class OrderChecking(flask_restplus.Resource):
    @ns.expect(_order_details, validate=True)
    @ns.marshal_with(app.api.response.order.order_details_response)
    def post(self):
        data = request.args or request.json
        return order.get_order_details(data)


_order_checking = ns.model('order_checking', app.api.request.order.order_checking_req)


@ns.route('/check', methods=['POST'])
class OrderChecking(flask_restplus.Resource):
    @ns.expect(_order_checking, validate=True)
    @ns.marshal_with(app.api.response.order.order_checking_response)
    def post(self):
        data = request.args or request.json
        return order.check_order(**data)


_order_creating = ns.model('order_creating', app.api.request.order.order_req)


@ns.route('/create', methods=['POST'])
class CreateOrder(flask_restplus.Resource):
    @ns.expect(_order_creating, validate=True)
    @ns.marshal_with(app.api.response.order.order_creating_response)
    def post(self):
        data = request.args or request.json
        return order.create_order(**data)


@ns.route('/qr_code/<order_id>', methods=['GET'])
class GetQrCode(flask_restplus.Resource):
    def get(self, order_id: int):
        return order.generate_qr_code(order_id)


@ns.route('/confirm/<token>', methods=['GET'])
class ConfirmOrder(flask_restplus.Resource):
    def get(self, token: int):
        order_id = decode_token(token)
        return order.pay_a_order(int(order_id))
