# coding=utf-8
import logging

from elasticsearch_dsl import Search, query

from app.repositories.es_base import EsRepositoryInterface

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


class KeywordElasticRepo(EsRepositoryInterface):
    def __init__(self):
        super().__init__()
        self._index = 'keywords'
        self.id_key = 'id'

    def search(self, args):
        """
        exec query and return response
        :param args:
        :return:
        """
        keyword_es = self.build_first_query(args)
        responses = keyword_es.using(self.es).index(self._index).execute()
        return responses.to_dict()

    def build_first_query(self, args):
        """
        First version: match all, sort theo score
        :return:
        """
        keyword_search_condition = query.Bool(
            must=self.build_first_text_query(args)
        )
        keyword_es = self.build_keyword_es(args, keyword_search_condition)
        return keyword_es

    def build_first_text_query(self, args):
        vn_text_query = args.get('search_text')
        na_text_query = args.get('q')
        text_query = query.DisMax(
            queries=[
                self.vietnames_prefix_query(vn_text_query),
                self.non_accented_prefix_query(na_text_query)
            ],
            tie_breaker=0
        )
        return text_query

    def vietnames_prefix_query(self, vn_text_query):
        return query.Prefix(
            keyword__raw={
                'value': vn_text_query,
                'boost': 1000
            }
        )

    def non_accented_prefix_query(self, na_text_query):
        return query.Prefix(
            keyword_no_tone__raw={
                'value': na_text_query,
                'boost': 100
            }
        )

    def build_keyword_es(self, args, keyword_search_condition):
        keyword_es = Search() \
            .query(keyword_search_condition)
        keyword_es = keyword_es.sort(*self.sort_condition(args))
        keyword_es = self.add_page_limit_to_keyword_es(args, keyword_es)
        return keyword_es

    def add_page_limit_to_keyword_es(self, args, keyword_es):
        _limit = args['_limit']
        keyword_es = keyword_es[0:_limit]
        return keyword_es

    def sort_condition(self, args):
        return [self.sort_by_score(), self.sort_by_number_of_occurrences()]

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

    def sort_by_number_of_occurrences(self):
        return {
            'count': {
                'order': 'desc'
            }
        }
