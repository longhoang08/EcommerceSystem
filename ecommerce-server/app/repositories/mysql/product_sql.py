# coding=utf-8
import logging
from typing import List

from app import models as m
from app.models.mysql.product_sql import ProductSQL

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def save(stock: ProductSQL, commit=False):
    m.db.session.add(stock)
    if commit:
        m.db.session.commit()
    return stock


def save_product_to_database(**kwargs) -> ProductSQL:
    stock = ProductSQL(**kwargs)
    return save(stock)


def upsert_product_to_database(sku: str, stock: int, price: int) -> ProductSQL:
    product_stock = get_product_by_sku(sku)
    if not product_stock:
        return save_product_to_database(sku=sku, stock=stock, price=price)
    product_stock.stock = stock
    return save(product_stock)


def get_product_by_skus(skus: List[str]) -> List[ProductSQL]:
    stocks = ProductSQL.query.filter(
        ProductSQL.sku.in_(skus)
    ).all()
    return stocks


def get_product_by_sku(sku: str) -> ProductSQL:
    stock = ProductSQL.query.filter(
        ProductSQL.sku == sku
    ).first()
    return stock if stock else None
