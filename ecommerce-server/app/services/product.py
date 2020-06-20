# coding=utf-8
import logging

from app.helpers.catalog.product_utils import Utilities
from app.repositories.product import ProductElasticRepo

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def get_result_search(args):
    args = Utilities.reformat_product_searchparams(args)
    product_es = ProductElasticRepo()
    response = product_es.search(args)
    return extract_product_data_from_response(response)


def extract_product_data_from_response(responses):
    if not responses:
        return {'result': {'products': []}}
    hits = responses['hits']['hits']
    products = [item['_source'] for item in hits]
    return {'result': {'products': products}}
