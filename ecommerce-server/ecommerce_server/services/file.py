# coding=utf-8
import logging

from ecommerce_server.repositories.product import ProductElasticRepo

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def get_all_files(args):
    file_es = ProductElasticRepo()
    responses = file_es.search(args)
    files = extract_file_data_from_response(responses)
    return files


def extract_file_data_from_response(responses):
    if not responses:
        return {'result': {'files': []}}
    responses = responses.to_dict()
    hits = responses['hits']['hits']
    files = [item['_source'] for item in hits]
    return {'result': {'files': files}}
