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
