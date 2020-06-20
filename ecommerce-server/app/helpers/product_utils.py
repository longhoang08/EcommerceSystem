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
    def reformat_params(args):
        unchanged_params = ['channel', '_page', '_limit', 'q', 'only_sku', 'DISABLE_SIGN', 'terminal', 'source',
                            'storeCodes', 'displayCategories', 'campaigns', 'promotionApplyOn']
        query_keyword = args.get('q')
        args['q'] = query_keyword[:255] if query_keyword is not None and len(query_keyword) else query_keyword
        if args.get('_limit') is not None and args.get('_limit') > 1000:
            args['_limit'] = 10
        publish_status_filter = args.get('publishStatus')
        args['publishStatus'] = None if publish_status_filter is None else (
                publish_status_filter.lower().strip() == 'true')
        is_bundle = args.get('isBundle')
        if is_bundle is not None and isinstance(is_bundle, bool):
            args['isBundle'] = int(is_bundle)
        else:
            args['isBundle'] = None if is_bundle is None else int(is_bundle.lower().strip() == 'true')
        for k, v in args.items():
            if k in unchanged_params and v is not None and isinstance(v, str):
                v = v.strip()
            if v is not None and isinstance(v, float) and (k == 'flashSale_gte' or k == 'flashSale_lte'):
                v = datetime.utcfromtimestamp(v).strftime(ISO_DATETIME_FORMAT)
            if k == 'flashSaleType' and v is not None:
                args[k] = v.strip()
                continue
            if k.endswith('_gte') or k.endswith("_lte"):
                args[k] = v
                continue
            if k not in unchanged_params and isinstance(v, str):
                v = v.split(',')
            if k not in unchanged_params and isinstance(v, list):
                v = list(filter(None, map(str.strip, v)))
            args[k] = v

        search_text = args.get('q')
        args['q_source'] = search_text
        search_text = normalize_text(search_text)
        args['search_text'] = search_text

        args['q'] = remove_vi_accent(search_text) if \
            args.get('q') is not None else None

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
