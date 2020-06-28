# coding=utf-8
import logging

from typing import Callable, List, Any

from elasticsearch import Elasticsearch
from elasticsearch_dsl import Search, query

from app.repositories.mysql import stock as stock_repo

from config import ELASTIC_HOST

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


class Ingestion():
    def __init__(self, index, condition):
        self.es = Elasticsearch(hosts=ELASTIC_HOST)
        self.index = index
        self.condition = condition

    def extract_data(self, documents):
        documents = [document['_source'] for document in documents]
        return documents

    def bash_ingest_from_es(self, page, limit):
        es = self.build_es_with_page_and_limit(page, limit)
        response = es.using(self.es).index(self.index).execute()
        return self.extract_data(response.to_dict()['hits']['hits'])

    def ingest_all(self, bash_size: int, handle_documents: Callable[[List[dict]], Any]):
        page = 1
        while True:
            documents = self.bash_ingest_from_es(page, bash_size)
            if len(documents) == 0: break
            handle_documents(documents)
            print("Ingested " + str(page * bash_size) + " documents")
            page += 1

    def build_es_with_page_and_limit(self, page, limit):
        product_es = Search().sort("_id") \
                         .query(self.condition())[(page - 1) * limit: page * limit]
        return product_es

    def sort_condition(self):
        return [self.sort_by_score()]

    def sort_by_score(self):
        return {
            '_score': {
                'order': 'desc'
            }
        }


def product_query_condition():
    condition = query.Bool(must=[
        query.MatchAll()
    ])
    # print(json.dumps(condition.to_dict()))
    return condition


def migrate_stock_data(products: List[dict]):
    for product in products:
        stock = product.get('stock') or {}
        stock = stock.get('in_stock') or 0
        sku = product['sku']
        stock_repo.upsert_stock_to_database(sku, stock)
