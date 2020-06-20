# coding=utf-8
import logging

from app.helpers.product_utils import Utilities, Converter
from app.repositories.product import ProductElasticRepo

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def get_result_search(args):
    args = Utilities.reformat_params(args)
    product_es = ProductElasticRepo()
    response = product_es.search(args)
    return extract_product_data_from_response(response)


def get_all_products(args):
    product_es = ProductElasticRepo()
    responses = product_es.search(args)
    products = extract_product_data_from_response(responses)
    return products


def extract_product_data_from_response(responses):
    if not responses:
        return {'result': {'files': []}}
    responses = responses.to_dict()
    hits = responses['hits']['hits']
    products = [item['_source'] for item in hits]
    return {'result': {'products': products}}
