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


def validate_upsert_product_request(args):
    from app.helpers.catalog import brands_data, categories_data
    if args.get('brand_code') not in brands_data or args.get('categories_code') not in categories_data:
        return False
    return True


def validate_product_details_param(args):
    pass
