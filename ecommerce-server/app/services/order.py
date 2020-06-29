# coding=utf-8
import logging
import threading
from typing import List, Any

from sqlalchemy import func

from app import BadRequestException
from app.repositories.mysql import product_sql

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

lock = threading.Lock()


def get_stock_details(skus: List[str]):
    pass


def check_order(skus: List[str], **kwargs) -> [List[dict], float]:
    order_reponse, total_price, _, failed_skus = _get_order_details(skus)
    return {
        'prices': order_reponse,
        'total_price': total_price,
        'failed_skus': failed_skus
    }


ORDER_FAILED_RESPONSE = {
    'status': 'failed',
    'message': 'SSome products are out of stock or out of sales. Please reload the order',
}


def create_order(skus: List[str], expected_price: float):
    with lock:
        order_reponse, total_price, products, failed_skus = _get_order_details(skus)
        if total_price != expected_price or failed_skus:
            return ORDER_FAILED_RESPONSE
        for index, product in enumerate(products):
            product.stock -= 1
            if order_reponse[index].get('promotion_price') > 0:
                product.promotion_price -= 1
            product_sql.save(product)
    return {
        'code': 'ok',
        'total_price': total_price
    }


# ======================================================================================================================
def _handle_failed_skus(skus: List[str], recieved_skus: List[str]):
    failed_skus = []
    for sku in skus:
        if sku not in recieved_skus:
            failed_skus.append(sku)
    raise BadRequestException('Product with sku ' + ','.join(failed_skus) + 'not exists!')


def _get_order_details(skus: List[str]) -> [List[dict], float, Any]:
    products = product_sql.get_product_by_skus(skus)
    if len(products) != len(skus):
        product_skus = [product.sku for product in products]
        _handle_failed_skus(product_skus, skus)
    order_reponse = []
    total_price = 0
    failed_skus = []
    for product in products:
        promotion_price = product.promotion_price \
            if (
                product.promotion_end_at != None
                and product.promotion_end_at > func.now()
                and product.promotion_stock > 0
        ) else 0
        if product.stock <= 0:
            failed_skus.append(product.sku)
            continue
        detail = {
            'sku': product.sku,
            'price': product.price,
            'promotion_price': promotion_price
        }

        total_price += promotion_price if promotion_price else product.price
        order_reponse.append(detail)

    return order_reponse, total_price, products, failed_skus
