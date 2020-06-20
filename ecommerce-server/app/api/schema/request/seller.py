# coding=utf-8
import logging
from flask_restplus import fields

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


seller_register_req = {
    'description': fields.String(required=True, description='description of seller')
}
