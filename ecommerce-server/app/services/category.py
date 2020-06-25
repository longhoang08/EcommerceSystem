# coding=utf-8
import logging

__author__ = 'LongHB'

from app.helpers.catalog.product_utils import Utilities
from app.repositories.es.category import CategoryElasticRepo

_logger = logging.getLogger(__name__)


def get_result_search(args):
    args = Utilities.reformat_search_text_search_params(args)
    category_es = CategoryElasticRepo()
    response = category_es.search(args)
    return extract_categories_data_from_response(response)


def extract_categories_data_from_response(responses):
    if not responses:
        return {'result': {'categories': []}}
    hits = responses['hits']['hits']
    keywords = [item['_source'] for item in hits]
    return {'categories': keywords}
