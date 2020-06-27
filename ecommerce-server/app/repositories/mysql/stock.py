# coding=utf-8
import logging
from typing import List

from app import models as m
from app.models.mysql.stock import Stock

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def save(stock: Stock):
    m.db.session.add(stock)
    # m.db.session.commit()
    return stock


def save_stock_to_database(**kwargs) -> Stock:
    stock = Stock(**kwargs)
    return save(stock)


def get_stock_by_skus(skus: List[str]) -> List[Stock]:
    stocks = Stock.query.filter(
        Stock.sku.in_(skus)
    )
    return stocks


def get_stock_by_sku(sku: str) -> Stock:
    stocks = get_stock_by_skus([sku])
    return stocks[0] if stocks else None
