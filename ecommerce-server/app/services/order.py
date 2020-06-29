# coding=utf-8
import logging
from typing import List

from app import BadRequestException
from app.repositories.mysql import product_sql

from app.services.product_sql import get_order_details

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

ORDER_FAILED_RESPONSE = {
    'status': 'failed',
    'message': 'SSome products are out of stock or out of sales. Please reload the order',
}


def get_stock_details(skus: List[str]):
    pass


def check_order(skus: List[str], **kwargs) -> [List[dict], float]:
    order_reponse, total_price, _, failed_skus = get_order_details(skus)
    return {
        'prices': order_reponse,
        'total_price': total_price,
        'failed_skus': failed_skus
    }


def create_order(skus: List[str], expected_price: float):
    from app.services import product_lock
    with product_lock:
        order_reponse, total_price, products, failed_skus = get_order_details(skus)
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


