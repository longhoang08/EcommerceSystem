# coding=utf-8
import logging

__author__ = 'LongHb'

from app import BadRequestException

_logger = logging.getLogger(__name__)


def validate_product_search_param(args):
    aggs = args.get('aggregations') or []
    supported_aggregations = ['brand', 'category']
    for agg in aggs:
        if agg not in supported_aggregations:
            raise BadRequestException('Only support aggregation of brand or category')

    sortables = args.get('sort') or []
    supported_sort_param = ['last_updated', 'most_discount', 'price_asc', 'price_desc']
    for sortable in sortables:
        if sortable not in supported_sort_param:
            raise BadRequestException('Only support sort param in ' + ','.join(supported_sort_param))


def validate_upsert_product_request(args):
    from app.helpers.catalog import is_valid_brand_code, is_valid_category_code
    return is_valid_brand_code(args.get('brand_code')) and is_valid_category_code(args.get('category_code'))


def validate_product_details_param(args):
    pass
