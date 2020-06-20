# coding=utf-8
import logging

from app.repositories.product import ProductElasticRepo

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

if __name__ == "__main__":
    es = ProductElasticRepo()
    es.create_index_if_not_exist()
