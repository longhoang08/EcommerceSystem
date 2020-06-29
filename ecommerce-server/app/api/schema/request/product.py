# coding=utf-8
import logging
from flask_restplus import fields

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

product_search_req = {
    'q': fields.String(required=False, description='Text query to search'),
    'seller_id': fields.Integer(required=False, description='Id of user to fetch product of this seller'),
    'skus': fields.List(fields.String(), required=False, description='List of skus to get details'),
    'only_promotion': fields.Boolean(required=False, description='Only get product which have promotion'),
    'only_flash_sale': fields.Boolean(required=False, description='Only get product which have flask sale'),
    'recommend': fields.Boolean(required=False, description='Only get recommend product'),
    'sort': fields.List(fields.String, required=False, description="Param to sort  products"),
    'aggregations': fields.List(fields.String, required=False,
                                description="List of attributes to aggregation (category, brand)"),
    '_page': fields.Integer(required=False, description='Paging, default to 1'),
    '_limit': fields.Integer(required=False, description='Number of product in response. Default to 10'),

    'category_codes': fields.List(fields.String, required=False),
    'brand_codes': fields.List(fields.String, required=False)
}

keyword_recommder_req = {
    'q': fields.String(required=False, description='Text query to recommend'),
    '_page': fields.Integer(required=False, description='Paging, default to 1'),
    '_limit': fields.Integer(required=False, description='Number of product in response. Default to 10'),
}

category_finding_req = {
    'q': fields.String(required=False, description='Text query to recommend'),
    '_page': fields.Integer(required=False, description='Paging, default to 1'),
    '_limit': fields.Integer(required=False, description='Number of product in response. Default to 10'),
}

brand_finding_req = {
    'q': fields.String(required=False, description='Text query to recommend'),
    '_page': fields.Integer(required=False, description='Paging, default to 1'),
    '_limit': fields.Integer(required=False, description='Number of product in response. Default to 10'),
}

category_details_req = {
    'code': fields.String(required=True, description='Code of categories'),
}

rating_req = {
    'star': fields.Integer(required=False, description='Get rating by star'),
    'sku': fields.String(required=True, description='Product sku to get rating'),
    '_page': fields.Integer(required=False, description='Paging, default to 1'),
    '_limit': fields.Integer(required=False, description='Number of product in response. Default to 10'),
}

rating_create_req = {
    'star': fields.Integer(required=True, description='Rating star (from 1-5)'),
    'sku': fields.String(required=True, description='Product sku to create rating'),
    'comment': fields.String(required=False, description='Rating comment')
}
