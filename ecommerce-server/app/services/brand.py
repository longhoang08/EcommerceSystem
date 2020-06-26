# coding=utf-8
import logging

__author__ = 'LongHB'

from app.helpers.catalog.product_utils import Utilities
from app.repositories.es.brand import BrandElasticRepo

_logger = logging.getLogger(__name__)


def get_result_search(args):
    args = Utilities.reformat_search_text_search_params(args)
    category_es = BrandElasticRepo()
    response = category_es.search(args)
    return extract_brand_data_from_response(response)


def extract_brand_data_from_response(responses):
    if not responses:
        return {'categories': []}
    hits = responses['hits']['hits']
    brands = [item['_source'] for item in hits]
    return {'brands': brands}
