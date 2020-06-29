# coding=utf-8
import logging

__author__ = 'LongHB'

from flask_restplus import fields

_logger = logging.getLogger(__name__)

order_req = {
    'skus': fields.List(fields.String(), required=True, description='List of skus to create order'),
    'expected_price': fields.Float()
}

order_checking_req = {
    'skus': fields.List(fields.String(), required=True, description='List of skus to create order')
}

order_details_req = {
    'order_id': fields.Integer(required=False, description='(Optional) Get details by order id'),
    'status': fields.Integer(required=False,
                             description='(Optional) status of order to filter (0-> chua thanh toan, 1-> dang van chuyen, 2->done'),
    '_page': fields.Integer(required=False, description='Paging, default to 1'),
    '_limit': fields.Integer(required=False, description='Number of product in response. Default to 10')
}
