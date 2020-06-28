# coding=utf-8
import logging
from typing import List

from app import models as m
from app.models.mysql.stock import Stock

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def save(stock: Stock, commit=False):
    m.db.session.add(stock)
    if commit:
        m.db.session.commit()
    return stock


def save_stock_to_database(**kwargs) -> Stock:
    stock = Stock(**kwargs)
    return save(stock)


def upsert_stock_to_database(sku: str, stock: int) -> Stock:
    product_stock = get_stock_by_sku(sku)
    if not product_stock:
        return save_stock_to_database(sku=sku, stock=stock)
    product_stock.stock = stock
    return save(product_stock)


def get_stock_by_skus(skus: List[str]) -> List[Stock]:
    stocks = Stock.query.filter(
        Stock.sku.in_(skus)
    )
    return stocks


def get_stock_by_sku(sku: str) -> Stock:
    stock = Stock.query.filter(
        Stock.sku == sku
    ).first()
    return stock if stock else None
