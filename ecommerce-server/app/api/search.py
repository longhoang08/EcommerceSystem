# coding=utf-8
import logging

import flask_restplus
from flask import request

from app.extensions import Namespace
from app.services import product
from . import requests

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

ns = Namespace('search', description='Search operations')

_search_req = ns.model('search_req', requests.search_req)


@ns.route('/', methods=['POST'])
class SearchProduct(flask_restplus.Resource):
    @ns.expect(_search_req, validate=True)
    # @ns.marshal_with(_search_res)
    def post(self):
        data = request.args or request.json
        return product.search(**data)


