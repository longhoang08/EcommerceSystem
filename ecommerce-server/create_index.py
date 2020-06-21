# coding=utf-8
import copy
import logging

import elasticsearch.helpers
from elasticsearch import Elasticsearch

from app.helpers.catalog import categories_data, is_leaf_category
from app.helpers.string_utils import remove_vi_accent
from app.repositories.es import bulk_update
from app.repositories.es.category import CategoryElasticRepo
from app.repositories.es.keyword import KeywordElasticRepo
from app.repositories.es.product import ProductElasticRepo

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def upsert_categories_data(bash_size: int = 100):
    current_size = 0
    current_categories = []
    for category_code in categories_data.keys():
        category = categories_data[category_code]
        category_id = category["id"]
        current_categories.append({
            **category,
            'name_no_tone': remove_vi_accent(category['name']),
            "is_last_level": is_leaf_category[category_id],
        })
        current_size += 1
        if current_size == bash_size:
            bulk_update('categories', copy.copy(current_categories), 'id')
            current_categories = []
            current_size = 0
    if current_categories:
        bulk_update('categories', copy.copy(current_categories), 'id')


if __name__ == "__main__":
    es_repos = [ProductElasticRepo(), CategoryElasticRepo(), KeywordElasticRepo()]
    for es_repo in es_repos:
        es_repo.create_index_if_not_exist()
    upsert_categories_data()
