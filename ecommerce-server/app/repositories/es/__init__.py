# coding=utf-8
import logging

__author__ = 'LongHB'

import elasticsearch.helpers
from elasticsearch import Elasticsearch
from elasticsearch_dsl import query

from config import ELASTIC_HOST

_logger = logging.getLogger(__name__)


def bulk_update(index_name, docs, key):
    es = Elasticsearch(hosts=ELASTIC_HOST)
    body = [
        {
            "retry_on_conflict": 5,
            "_op_type": 'update',
            "_id": doc.get(key),
            'doc': doc,
            "doc_as_upsert": True

        } for doc in docs
    ]
    res = elasticsearch.helpers.bulk(
        es, body, index=index_name,
        chunk_size=100,
        max_retries=5
    )
    return res


def build_prefix_query(key: str, term: str, boost_factor: int) -> query.Query:
    return query.Prefix(**{
        key: {
            "value": term,
            "boost": boost_factor
        }
    })


def build_phrase_prefix_query(key: str, term: str, boost_factor: int):
    return query.MatchPhrasePrefix(**{
        key: {
            "query": term,
            "boost": boost_factor
        }
    })


def build_match_query(key: str, term: str, fuzziness: str, boost_factor: int = 1) -> query.Query:
    return query.Match(**{
        key: {
            'query': term,
            'operator': 'or',
            "fuzziness": fuzziness,
            "minimum_should_match": "3<75%",
            'boost': boost_factor
        }
    })
