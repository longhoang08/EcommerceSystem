# coding=utf-8
import json
import logging
from typing import List

from elasticsearch_dsl import query, Search, A

from app.repositories.es import build_phrase_prefix_query, build_match_query, build_prefix_query
from config import FILES_INDEX
from app.models.es.product import mappings, settings
from app.repositories.es.es_base import EsRepositoryInterface

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

KEYWORD_NAME_FIELD = 'search_text.raw'
KEYWORD_NAME_NO_TONE_FIELD = 'search_text_no_tone.raw'
SEARCH_TEXT_FIELD = 'search_text'
SEARCH_TEXT_NO_TONE_FIELD = 'search_text_no_tone'


class ProductElasticRepo(EsRepositoryInterface):
    def __init__(self):
        super().__init__()
        self._index = FILES_INDEX
        self.mappings = mappings
        self.settings = settings
        self.id_key = 'sku'

    def search(self, args):
        """
        Build queries for searching products
        :param dict args: arguments
        :return: result searching
        :rtype: List
        """
        if args.get("skus"):
            product_es = self.build_sku_query(args)
            responses = product_es.using(self.es).index(self._index).execute()
            responses = responses.to_dict()
        else:
            have_text_query = args['q'] is not None
            if have_text_query:
                queries = [self.build_first_query(args), self.build_second_query(args)]
                responses, query_index = self.multiple_query(self._index, queries)
            else:
                product_es = self.build_first_query(args)
                responses = product_es.using(self.es).index(self._index).execute()
                responses = responses.to_dict()
        return responses

    def build_file_es(self, args, search_condition):
        product_es = Search() \
            .query(search_condition)
        product_es = product_es.sort(*self.sort_condition(args))
        product_es = self.add_page_limit_to_es(args, product_es)
        return product_es

    def add_page_limit_to_es(self, args, product_es):
        _limit = args.get('_limit') if args.get('_limit') else 20
        product_es = product_es[0:_limit]
        return product_es

    def sort_condition(self, args):
        return [self.sort_by_score()]

    def sort_by_score(self):
        return {
            '_score': {
                'order': 'desc'
            }
        }

    def get_filter_condition(self, args):
        return query.MatchAll()

    @staticmethod
    def add_page_limit_to_product_es(args, product_es):
        _page = args.get('_page') or 1
        _limit = args.get('_limit') or 10
        # Pagination
        product_es = product_es[(_page - 1) * _limit: _page * _limit]
        return product_es

    def build_product_es(self, args, product_search_condition, sources):
        product_es = Search() \
            .query(product_search_condition) \
            .source(['sku'] if args.get('only_sku') else sources) \
            .extra(track_total_hits=True)
        product_es = product_es.sort(*self.sort_condition(args))
        return product_es

    @staticmethod
    def build_sources(args):
        return ["name", "categories", "attributes", "seller", "brand", "description", "images", "price", "quantity",
                "sku", "stock"]

    def build_product_es_from_text_query_condition(self, args, text_query_condition):
        product_search_condition = text_query_condition
        sources = self.build_sources(args)
        product_es = self.build_product_es(args, product_search_condition, sources)
        self.add_filter_to_product_es(args, product_es)
        product_es = self.add_page_limit_to_product_es(args, product_es)
        return product_es

    def add_filter_to_product_es(self, args, product_es):
        aggregations = args.get('aggregations') or []
        if 'brand' in aggregations:
            self.add_brands_aggregation_to_product_es(product_es)
        if 'category' in aggregations:
            self.add_categories_aggregation_to_product_es(product_es)
        return product_es

    @staticmethod
    def add_brands_aggregation_to_product_es(product_es):
        brands_aggregation = A(
            'terms',
            script="if (doc['brand.code'].size() != 0) "
                   "doc['brand.code'].value + '|' + doc['brand.name'].value",
            size=50)
        product_es.aggs.bucket('brands', brands_aggregation)

    @staticmethod
    def add_categories_aggregation_to_product_es(product_es):
        """
        Add sellter categories to products es
        Key in format {code}|{name}|{level}|{id}|{parentID}|{count}
        :param product_es:
        :return:
        """
        product_es.aggs.bucket('categories', 'nested', path='categories') \
            .bucket('data', 'terms', field="categories.code", size=50)

    # Text query only =================================================================================================

    def build_sku_query(self, args: dict):
        skus = args.get('skus') or []
        query_conditions = query.Bool(filter=[
            query.Terms(sku=skus)
        ])
        products_es = self.build_product_es_from_text_query_condition(args, query_conditions)
        # print(json.dumps(products_es.to_dict()))
        return products_es

    def build_first_query(self, args) -> query.Query:
        """
        Nếu khớp với 1 sku hoặc 1 phần của 1 sku nào đó, query sẽ trả về sản phẩm có sku đó
        Query khớp hoàn toàn với tên sản phẩm, không cho phép khoảng trống cũng như đổi thứ tự
        Query sẽ trả về match all nếu text truyền vào rỗng
        :param args:
        :return: product_es
        """
        text_source = args.get('q_source')
        search_text = args.get('search_text')
        search_text_no_tone = args.get('q')
        text_query_condition = query.Bool(
            must=[
                query.Bool(
                    should=[query.MatchAll()] if search_text is None else
                    [
                        *self.build_sku_match_conditions(text_source),
                        *self.build_extract_match_text_conditions(search_text, search_text_no_tone, args)
                    ]
                ),
                query.Exists(field="name"),
                query.Exists(field="seller")
            ],
            filter=self.get_filter_condition(args)
        )
        products_es = self.build_product_es_from_text_query_condition(args, text_query_condition)
        return products_es

    def build_second_query(self, args):
        """
        Hàm chỉ được gọi khi có search text
        :param args:
        :param fuzziness:
        :return:
        """
        fuzzinees = "AUTO"  # Use auto fuzzinees for second query

        search_text = args.get('search_text')

        text_query_condition = query.Bool(
            must=[
                query.Exists(field="name"),
                query.Exists(field="seller")
            ],
            should=[
                build_match_query(SEARCH_TEXT_FIELD, search_text, fuzzinees, 2),
                build_match_query(SEARCH_TEXT_NO_TONE_FIELD, search_text, fuzzinees)

            ],
            minimum_should_match=1,
            filter=self.get_filter_condition(args)
        )

        products_es = self.build_product_es_from_text_query_condition(args, text_query_condition)
        # print(json.dumps(products_es.to_dict()))
        return products_es

    @staticmethod
    def build_sku_match_conditions(text_source: str) -> List[query.Query]:
        return [
            query.Term(sku__raw={
                "value": text_source,
                "boost": pow(10, 7)
            }),
            query.Prefix(sku__raw={
                "value": text_source,
                "boost": pow(10, 6)
            })
        ]

    @staticmethod
    def build_extract_match_text_conditions(search_text: str, search_text_no_tone: str, args: dict) \
            -> List[query.Query]:
        fuzzinees = "0"  # Don't use fuzzy in first query

        return [
            build_prefix_query(KEYWORD_NAME_FIELD, search_text, pow(10, 5) * 2),
            build_prefix_query(KEYWORD_NAME_NO_TONE_FIELD, search_text_no_tone, pow(10, 5)),

            build_phrase_prefix_query(SEARCH_TEXT_FIELD, search_text, pow(10, 3) * 2),
            build_phrase_prefix_query(
                SEARCH_TEXT_NO_TONE_FIELD, search_text_no_tone, pow(10, 3)),

            build_match_query(SEARCH_TEXT_FIELD, search_text, fuzzinees, 2),
            build_match_query(SEARCH_TEXT_NO_TONE_FIELD, search_text, fuzzinees)
        ]

    def multiple_query(self, index, queries):
        search_queries = []

        for query in queries:
            search_queries.append({
                'index': index
            })
            search_queries.append(query.to_dict())
        query_request = ''
        for search_query in search_queries:
            query_request += '%s \n' % json.dumps(search_query)
        es = self.es
        resp = es.msearch(body=query_request, index=self._index)
        msearch_responses = resp["responses"]
        for query_index, msearch_response in enumerate(msearch_responses):
            if not self.is_empty_responses(msearch_response):
                responses = msearch_response
                return responses, query_index
        return msearch_responses[0], 0

    @staticmethod
    def is_empty_responses(responses):
        number_of_products = responses['hits']['total']['value']
        return number_of_products == 0
