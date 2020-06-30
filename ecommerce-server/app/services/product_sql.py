# coding=utf-8
import logging
from typing import List, Any

from sqlalchemy import func

from app import BadRequestException
from app.repositories.mysql import product_sql
from app.repositories.mysql import product_sql as repo

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def update_product_to_mysql(sku: str, price: int, stock_changed: int = 0):
    from app.services import product_lock
    with product_lock:
        product = repo.get_product_by_sku(sku)
        if not product:
            stock_changed = max(stock_changed, 0)
            return repo.save_product_to_database(sku=sku, price=price, stock=stock_changed)
        product.stock = max(product.stock + stock_changed, 0)
        return repo.save(product)


def get_order_stock_details(skus: List[str]) -> [List[dict], float, Any]:
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


# ======================================================================================================================
def _handle_failed_skus(skus: List[str], recieved_skus: List[str]):
    failed_skus = []
    for sku in skus:
        if sku not in recieved_skus:
            failed_skus.append(sku)
    raise BadRequestException('Product with sku ' + ','.join(failed_skus) + 'not exists!')
