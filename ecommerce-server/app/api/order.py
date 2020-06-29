# coding=utf-8
import logging

import flask_restplus
from flask import request

import app.api.schema.request.product
from app.services import order
from app.extensions import Namespace

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

ns = Namespace('order', description='Order management operations')

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
        pass
