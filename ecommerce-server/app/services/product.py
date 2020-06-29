# coding=utf-8
import logging
from typing import List

from app.helpers.catalog.product_utils import Utilities
from app.models import ProductSQL
from app.repositories.es.product import ProductElasticRepo
from app.repositories.es import ingestion

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def update_stocks(products: List[ProductSQL]):
    es_products = [{
        'stock': {
            'in_stock': product.stock,
            'in_stock_sortable': 0 if not product.stock else 1
        },
        'sku': product.sku
    } for product in products]
    ingestion.upsert_products(es_products)


def find_by_sku(sku: str):
    product_es = ProductElasticRepo()
    responses = product_es.search({'skus': [sku]})
    products = extract_only_products_from_response(responses)
    return products[0] if products else None


def get_result_search(args):
    args = Utilities.reformat_product_search_params(args)
    product_es = ProductElasticRepo()
    response = product_es.search(args)
    return extract_product_data_from_response(args, response)


def extract_only_products_from_response(responses):
    if not responses:
        return []
    hits = responses['hits']['hits']
    products = [item['_source'] for item in hits]
    return products


def extract_product_data_from_response(args, responses):
    result = {
        'data': {
            'total': get_total_products(responses),
            'products': extract_only_products_from_response(responses)
        }
    }
    aggregations = args.get('aggregations') or []
    aggregations_result = {}
    if 'brand' in aggregations:
        aggregations_result['brand'] = get_brand_aggregation(responses)
    if 'category' in aggregations:
        aggregations_result['category'] = get_category_aggregation(responses)

    if aggregations_result:
        result['data']['aggregations'] = aggregations_result

    return result


def get_total_products(response):
    total = response.get('hits') or {}
    total = total.get('total') or {}
    total = total.get('value') or 0
    return total


def get_brand_aggregation(response):
    aggregation = response.get('aggregations') or {}
    brand = aggregation.get('brands') or {}
    buckets = brand.get('buckets') or []
    return extract_buckets(buckets)


def get_category_aggregation(response):
    aggregation = response.get('aggregations') or {}
    category = aggregation.get('categories') or {}
    data = category.get('data') or {}
    buckets = data.get('buckets') or []
    return extract_category_buckets(buckets)


def extract_category_buckets(buckets):
    responses = []
    for bucket in buckets:
        try:
            category_code = bucket.get('key')
            from app.helpers.catalog import categories_data
            category = categories_data.get(category_code)
            name = category.get('name')
            responses.append({
                'code': category_code,
                'name': name,
                'count': bucket.get('doc_count')
            })
        except Exception:
            pass
    return responses


def extract_buckets(buckets):
    responses = []
    for bucket in buckets:
        try:
            keys = bucket.get('key').split('|')
            responses.append({
                'code': keys[0],
                'name': keys[1],
                'count': bucket.get('doc_count')
            })
        except Exception:
            pass
    return responses
