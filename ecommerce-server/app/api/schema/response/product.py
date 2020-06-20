# coding=utf-8
import logging

__author__ = 'LongHB'

from flask_restplus import fields

from app.api import api

_logger = logging.getLogger(__name__)

keyword_recommenders_data = api.model('recommend', {
    'query': fields.String(attribute='keyword'),
})

keyword_recommenders_response = api.model('keyword_recommenders_response', {
    'code': fields.String(),
    'result': fields.Nested(model=api.model('keywords', {
        'keywords': fields.Nested(keyword_recommenders_data)
    }))
})
