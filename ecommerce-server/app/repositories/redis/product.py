# coding=utf-8
import logging

__author__ = 'LongHB'

from app.repositories.redis import RedisCacheBase

_logger = logging.getLogger(__name__)


class ProductCache(RedisCacheBase):

    def __init__(self):
        super().__init__("products", ttl=900)

    def fetch_data(self, key: str):
        pass
