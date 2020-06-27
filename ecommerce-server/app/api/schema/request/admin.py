# coding=utf-8
import logging
from flask_restplus import fields

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

get_pending_seller_req = {
    'q': fields.String(required=False, description='Text query to recommend'),
    '_page': fields.Integer(required=False, description='Paging, default to 1'),
    '_limit': fields.Integer(required=False, description='Number of product in response. Default to 10'),
}

confirm_seller_req = {
    'ids': fields.List(fields.Integer, required=True, description="List of pending seller to confim")
}
