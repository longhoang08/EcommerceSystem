# coding=utf-8
import logging

__author__ = 'LongHB'

import elasticsearch.helpers
from elasticsearch import Elasticsearch

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
