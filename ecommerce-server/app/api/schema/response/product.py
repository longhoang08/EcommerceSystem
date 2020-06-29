# coding=utf-8
import logging
from flask_restplus import fields

from app.api import api

__author__ = 'LongHB'
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

rating_data = api.model('rating_data', {
    'user_name': fields.String(),
    'star': fields.Integer(),
    'comment': fields.String(),
    'created_at': fields.DateTime()
})

rating_response = api.model('rating_response', {
    'ratings': fields.Nested(rating_data),
    'total_rating': fields.Float()
})
