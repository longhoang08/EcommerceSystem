# coding=utf-8
import logging
from typing import List

from config import PRODUCT_INDEX

__author__ = 'LongHB'

_logger = logging.getLogger(__name__)


def upsert_product(product: dict) -> dict:
    from app.repositories.es import bulk_update
    bulk_update(PRODUCT_INDEX, [product], 'sku')
    return product


def upsert_products(products: List[dict]):
    from app.repositories.es import bulk_update
    bulk_update(PRODUCT_INDEX, products, 'sku')
    return products
