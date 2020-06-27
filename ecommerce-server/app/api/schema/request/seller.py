# coding=utf-8
import logging
from flask_restplus import fields

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

seller_register_req = {
    'description': fields.String(required=True, description='description of seller')
}

ingest_data_req = {
    'sku': fields.String(required=True),
    'brand_code': fields.String(requred=True),
    'categories_code': fields.String(requred=True),
    'images_url': fields.List(fields.String(), required=False),
    'description': fields.String(requred=False),
    'name': fields.String(requred=True),
    'price': fields.Integer(required=True),
    'stock_changed': fields.Integer(required=False)
}
