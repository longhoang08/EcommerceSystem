# coding=utf-8
import logging

from elasticsearch_dsl import query, Search

from config import FILES_INDEX
from ecommerce_server.models.product import mappings, settings
from ecommerce_server.repositories.es_base import EsRepositoryInterface

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


class ProductElasticRepo(EsRepositoryInterface):
    def __init__(self):
        super().__init__()
        self._index = FILES_INDEX
        self.mappings = mappings
        self.settings = settings
        self.id_key = 'sku'

    def search(self, args):
        """
        exec query and return response
        :param args:
        :return:
        """
        product_es = self.build_query(args)
        # print(json.dumps(product_es.to_dict()))
        responses = product_es.using(self.es).index(self._index).execute()
        return responses

    def build_query(self, args):
        """
        Build query for es
        :param args:
        :return:
        """
        conditions = query.Bool(
            must=query.MatchAll()
        )
        product_es = self.build_file_es(args, conditions)
        return product_es

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
