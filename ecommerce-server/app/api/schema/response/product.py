# coding=utf-8
import logging

__author__ = 'LongHB'

from flask_restplus import fields

from app.api import api

_logger = logging.getLogger(__name__)

_keyword_recommenders_data = api.model('keyword_recommenders_data', {
    'query': fields.String(attribute='keyword'),
})

keyword_recommenders_response = api.model('keyword_recommenders_response', {
    'keywords': fields.Nested(_keyword_recommenders_data)
})

_category_choosable_data = api.model('category_choosable_data', {
    'code': fields.String(),
    'id': fields.Integer(),
    'name': fields.String(),
})

category_choosable_response = api.model('category_choosable_response', {
    'categories': fields.Nested(_category_choosable_data)
})

_brand_choosable_data = api.model('brand_choosable_data', {
    'code': fields.String(),
    'id': fields.Integer(),
    'name': fields.String(),
    'description': fields.String(),
})

brand_choosable_response = api.model('brand_choosable_response', {
    'brands': fields.Nested(_brand_choosable_data)
})
