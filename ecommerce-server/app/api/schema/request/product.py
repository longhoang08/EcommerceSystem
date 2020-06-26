# coding=utf-8
import logging
from flask_restplus import fields

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

product_search_req = {
    'q': fields.String(required=False, description='Text query to search'),
    'skus': fields.List(fields.String(), required=False, description='List of skus to get details'),
    'only_promotion': fields.Boolean(required=False, description='Only get product which have promotion'),
    'only_flash_sale': fields.Boolean(required=False, description='Only get product which have flask sale'),
    'sort': fields.List(fields.String, required=False, description="Param to sort  products"),
    '_page': fields.Integer(required=False, description='Paging, default to 1'),
    '_limit': fields.Integer(required=False, description='Number of product in response. Default to 10'),
}

keyword_recommder_req = {
    'q': fields.String(required=False, description='Text query to recommence'),
    '_page': fields.Integer(required=False, description='Paging, default to 1'),
    '_limit': fields.Integer(required=False, description='Number of product in response. Default to 10'),
}

category_finding_req = {
    'q': fields.String(required=False, description='Text query to recommence'),
    '_page': fields.Integer(required=False, description='Paging, default to 1'),
    '_limit': fields.Integer(required=False, description='Number of product in response. Default to 10'),
}

category_details_req = {
    'code': fields.String(required=True, description='Code of categories'),
}
