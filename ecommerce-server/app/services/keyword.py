# coding=utf-8
import logging

__author__ = 'LongHB'

from app.helpers.catalog.product_utils import Utilities
from app.repositories.keyword import KeywordElasticRepo

_logger = logging.getLogger(__name__)


def get_result_search(args):
    args = Utilities.reformat_keyword_search_params(args)
    keyword_es = KeywordElasticRepo()
    response = keyword_es.search(args)
    return extract_keywords_data_from_response(response)


def extract_keywords_data_from_response(responses):
    if not responses:
        return {'result': {'keywords': []}}
    hits = responses['hits']['hits']
    print(hits)
    keywords = [item['_source'] for item in hits]
    return {'result': {'keywords': keywords}}
