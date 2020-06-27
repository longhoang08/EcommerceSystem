# coding=utf-8
import logging

from app.repositories.redis import RedisCacheBase
from app.repositories.mysql import stock as mysql_repo

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


class StockCache(RedisCacheBase):
    def __init__(self):
        super().__init__("stock")

    def fetch_data(self, key: str):
        return mysql_repo.get_stock_by_sku(key).stock


class PromotionStockCache(RedisCacheBase):
    def __init__(self):
        super().__init__("stock:promotion")

    def fetch_data(self, key: str):
        return mysql_repo.get_stock_by_sku(key).promotion_stock


class FlashSaleStockCache(RedisCacheBase):
    def __init__(self):
        super().__init__("stock:flashsale")

    def fetch_data(self, key: str):
        return mysql_repo.get_stock_by_sku(key).flash_sale_stock
