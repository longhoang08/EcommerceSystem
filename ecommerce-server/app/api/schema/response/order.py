# coding=utf-8
import logging
from flask_restplus import fields

from app.api import api

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

_order_price = api.model('order_price', {
    'sku': fields.String(),
    'price': fields.Float(),
    'promotion_price': fields.Float()
})

order_checking_response = {
    'prices': fields.Nested(_order_price),
    'total_price': fields.Float()
}

order_response = {
    'status': fields.String(description='status of this action (ok or failed)'),
    'failed_sku': fields.List(fields.String(), description='List of sku out of stocks'),
    'message': fields.String(description='optional message'),
    'prices': fields.Nested(_order_price)
}

order_checking_response = api.model('order_checking_response', {
    'prices': fields.Nested(_order_price),
    'total_price': fields.Float(),
    'failed_sku': fields.List(fields.String(), description='List of sku out of stocks'),
})

order_creating_response = api.model('order_response', order_response)
