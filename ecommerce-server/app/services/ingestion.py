# coding=utf-8
import logging

from app.repositories.es import ingestion as repo

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def upsert_product(product):
    product = repo.upsert_product(product)
    del product['search_text']
    del product['search_text_no_tone']
    return product
