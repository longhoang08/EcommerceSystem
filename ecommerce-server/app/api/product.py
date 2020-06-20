# coding=utf-8
import logging

import flask_restplus
from flask import request

from app.extensions import Namespace
from app.services import product
from . import requests

__author__ = 'LongHB'

from ..helpers.product_validator import validate_search_param

_logger = logging.getLogger(__name__)

ns = Namespace('product', description='Product operations')

_search_req = ns.model('product_req', requests.search_req)


@ns.route('/', methods=['POST'])
class ProductDetails(flask_restplus.Resource):
    @ns.expect(_search_req, validate=True)
    # @ns.marshal_with(_search_res)
    def post(self):
        data = request.args or request.json
        validate_search_param(data)
        return product.get_result_search(data)

