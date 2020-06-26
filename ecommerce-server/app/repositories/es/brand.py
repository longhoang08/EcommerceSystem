# coding=utf-8
import logging

from elasticsearch_dsl import Search, query

from app.models.es.brand import mappings, settings
from app.repositories.es import build_prefix_query, build_phrase_prefix_query, build_match_query
from app.repositories.es.es_base import EsRepositoryInterface

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


class BrandElasticRepo(EsRepositoryInterface):
    def __init__(self):
        super().__init__()
        self._index = 'brands'
        self.id_key = 'id'
        self.mappings = mappings
        self.settings = settings

    def search(self, args):
        """
        exec query and return response
        :param args:
        :return:
        """
        brand = self.build_first_query(args)
        responses = brand.using(self.es).index(self._index).execute()
        return responses.to_dict()

    def build_first_query(self, args):
        """
        First version: match all, sort theo score
        :return:
        """
        brand_search_condition = query.Bool(
            must=self.build_first_text_query(args)
        )
        brand_es = self.build_brand_es(args, brand_search_condition)
        return brand_es

    def build_first_text_query(self, args):
        fuzzinees = "AUTO"
        vn_text_query = args.get('search_text')
        na_text_query = args.get('q')

        if not vn_text_query:
            return query.MatchAll()

        text_query = query.DisMax(
            queries=[
                build_prefix_query("name_no_tone__raw", na_text_query, 1000),

                build_phrase_prefix_query("name", vn_text_query, 200),
                build_phrase_prefix_query("name_no_tone", na_text_query, 100),

                build_phrase_prefix_query("description", vn_text_query, 50),

                build_match_query("name", vn_text_query, fuzzinees, 5),
                build_match_query("name_no_tone", na_text_query, fuzzinees),

                build_match_query("description", vn_text_query, fuzzinees, 1)
            ],
            tie_breaker=0.2
        )
        return text_query

    def build_brand_es(self, args, category_search_condition):
        keyword_es = Search() \
            .query(category_search_condition)
        keyword_es = keyword_es.sort(*self.sort_condition(args))
        keyword_es = self.add_page_limit_to_brand_es(args, keyword_es)
        return keyword_es

    def add_page_limit_to_brand_es(self, args, keyword_es):
        _limit = args['_limit']
        keyword_es = keyword_es[0:_limit]
        return keyword_es

    def sort_condition(self, args):
        return [self.sort_by_score()]

    def sort_by_score(self):
        return {
            '_score': {
                'order': 'desc'
            }
        }
