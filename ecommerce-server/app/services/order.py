# coding=utf-8
import logging
from typing import List

from sqlalchemy import func

from app import BadRequestException
from app.repositories.mysql import product_sql

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def get_stock_details(skus: List[str]):
    pass


def check_order(skus: List[str], **kwargs) -> [List[dict], float]:
    products = product_sql.get_product_by_skus(skus)
    if len(products) != len(skus):
        product_skus = [product.sku for product in products]
        _handle_failed_skus(product_skus, skus)
    order_reponse = []
    total_price = 0
    for product in products:
        promotion_price = product.promotion_price \
            if (product.promotion_end_at != None and product.promotion_end_at > func.now()) else 0

        detail = {
            'sku': product.sku,
            'price': product.price,
            'promotion_price': promotion_price
        }

        total_price += promotion_price if promotion_price else product.price
        order_reponse.append(detail)

    return order_reponse, total_price


def create_order(skus: List[str], expected_price: float) -> bool:
    pass


# ======================================================================================================================
def _handle_failed_skus(skus: List[str], recieved_skus: List[str]):
    failed_skus = []
    for sku in skus:
        if sku not in recieved_skus:
            failed_skus.append(sku)
    raise BadRequestException('Product with sku ' + ','.join(failed_skus) + 'not exists!')
