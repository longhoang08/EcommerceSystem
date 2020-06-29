# coding=utf-8
import logging
from typing import List

from app.repositories.mysql import order_product as repo

__author__ = 'LongHB'

_logger = logging.getLogger(__name__)


def create_new_order(order_id: int, skus: List[str]):
    for sku in skus:
        repo.create_new_order_product(order_id=order_id, sku=sku)
