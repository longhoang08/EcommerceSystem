# coding=utf-8
import logging

__author__ = 'LongHB'

from datetime import datetime, timezone
from typing import Dict

from app.helpers.string_utils import normalize_text, remove_vi_accent

_logger = logging.getLogger(__name__)

ISO_DATETIME_FORMAT = '%Y-%m-%dT%H:%M:%SZ'
SPLITER = " | "


class Utilities:
    @staticmethod
    def pagination(page, page_size, total_items):
        """
        :param int page:
        :param int page_size:
        :param int total_items:
        :return: total pages, next page, previous page
        :rtype: int, int, int
        """
        total_pages = total_items // page_size if total_items % page_size == 0 \
            else (total_items // page_size) + 1
        next_page = page + 1 if page < total_pages else None
        previous_page = page - 1 if page > 1 else None
        return total_pages, next_page, previous_page

    @staticmethod
    def reformat_product_search_params(args):
        args['q_source'] = args.get('q')

        args = Utilities.reformat_search_text_search_params(args)
        return args

    @staticmethod
    def reformat_search_text_search_params(args):
        args['_limit'] = args.get('_limit') or 10
        if args.get('_limit') > 1000 or args.get('_limit') < 1:
            args['_limit'] = 10
        args['_page'] = args.get('_page') or 1
        args['_page'] = max(args['_page'], 1)

        search_text = args.get('q')
        search_text = normalize_text(search_text)
        args['search_text'] = search_text
        args['q'] = remove_vi_accent(search_text) if search_text is not None else None

        return args


class Converter:
    @staticmethod
    def reformat_product(product):
        search_text = Converter.to_keyword_search_new(product)
        product['search_text'] = search_text
        product['search_text_no_tone'] = search_text

    @staticmethod
    def to_keyword_search_new(obj):
        name = obj.get('name', '')
        sku = obj.get('sku', '')
        categories = obj.get("categories", None)
        category_name = Converter._get_category_names(categories)

        return SPLITER.join([
            name,
            *[sku for _ in range(2)],
            *[category_name for _ in range(4)],
        ])

    @staticmethod
    def _get_category_names(categories: list) -> str:
        if not categories: return ''
        category_names = [category.get('name') for category in categories if category.get('name')]
        return SPLITER.join(category_names)

    @staticmethod
    def date_time_to_iso_string(date_local: datetime):
        date_utc = date_local.astimezone(timezone.utc)
        return date_utc.strftime(ISO_DATETIME_FORMAT)
