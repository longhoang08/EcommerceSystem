# coding=utf-8
import logging
from flask_restplus import fields

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


seller_res = {
    'id': fields.String(description='Id of seller'),
    'description': fields.String(description="Seller description, can be html or string"),
    'status': fields.String(description="Status of seller"),
}
