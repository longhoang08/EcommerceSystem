# coding=utf-8
import logging

from elasticsearch_dsl import Search, query

from app.models.es.category import mappings, settings
from app.repositories.es import build_prefix_query, build_phrase_prefix_query, build_match_query
from app.repositories.es.es_base import EsRepositoryInterface

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


class CategoryElasticRepo(EsRepositoryInterface):
    def __init__(self):
        super().__init__()
        self._index = 'categories'
        self.id_key = 'id'
        self.mappings = mappings
        self.settings = settings

    def search(self, args):
        """
        exec query and return response
        :param args:
        :return:
        """
        category_es = self.build_first_query(args)
        responses = category_es.using(self.es).index(self._index).execute()
        return responses.to_dict()

    def build_first_query(self, args):
        """
        First version: match all, sort theo score
        :return:
        """
        category_search_condition = query.Bool(
            must=self.build_first_text_query(args),
            filter=self.build_filter_conditions()
        )
        keyword_es = self.build_category_es(args, category_search_condition)
        return keyword_es

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
                build_match_query("name", vn_text_query, fuzzinees, 5),
                build_match_query("name_no_tone", na_text_query, fuzzinees),
            ],
            tie_breaker=0.2
        )
        return text_query

    def build_filter_conditions(self):
        return query.Term(is_last_level=True)

    def build_category_es(self, args, category_search_condition):
        keyword_es = Search() \
            .query(category_search_condition)
        keyword_es = keyword_es.sort(*self.sort_condition(args))
        keyword_es = self.add_page_limit_to_category_es(args, keyword_es)
        return keyword_es

    def add_page_limit_to_category_es(self, args, keyword_es):
        _limit = args['_limit']
        keyword_es = keyword_es[0:_limit]
        return keyword_es

    def sort_condition(self, args):
        return [self.sort_by_score()]

    def get_channel_filter_condition(self, channel):
        return query.Term(channel={
            "value": channel
        })

    def sort_by_score(self):
        return {
            '_score': {
                'order': 'desc'
            }
        }
