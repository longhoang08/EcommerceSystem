# coding=utf-8
import logging

import flask_restplus
from flask import request

import app.api.schema.request.product
from app.extensions import Namespace
from app.services import product, keyword

__author__ = 'LongHB'

from app.helpers.catalog.product_validator import validate_product_search_param

_logger = logging.getLogger(__name__)

ns = Namespace('product', description='Product operations')

_product_search_req = ns.model('product_search_req', app.api.request.product.product_search_req)
_category_finding_req = ns.model('_category_finding_req', app.api.request.product.category_finding_req)
_keyword_req = ns.model('keyword_recommend_req', app.api.request.product.keyword_recommder_req)


@ns.route('/keywords', methods=['POST'])
class KeywordRecommender(flask_restplus.Resource):
    @ns.expect(_keyword_req, validate=True)
    @ns.marshal_with(app.api.response.product.keyword_recommenders_response)
    def post(self):
        data = request.args or request.json
        return keyword.get_result_search(data)


@ns.route('/search', methods=['POST'])
class SearchProduct(flask_restplus.Resource):
    @ns.expect(_product_search_req, validate=True)
    def post(self):
        data = request.args or request.json
        validate_product_search_param(data)
        return {
            "categories": product.get_result_search(data)
        }


@ns.route('/categories/choosable', methods=['POST'])
class CategoriesChooseable(flask_restplus.Resource):
    @ns.expect(_category_finding_req, validate=True)
    def post(self):
        from app.helpers.catalog import all_leaf_categories
        return all_leaf_categories

# _product_details_req = ns.model('product_details_req', app.api.schema.request.product.product_search_req)
# @ns.route('/details', methods=['POST'])
# class ProductDetails(flask_restplus.Resource):
#     @ns.expect(_product_details_req, validate=True)
#     # @ns.marshal_with(_search_res)
#     def post(self):
#         data = request.args or request.json
#         validate_product_details_param(data)
#         return product.get_result_search(data)
