# coding=utf-8
import logging

__author__ = 'LongHB'

from app.repositories.redis import RedisCacheBase

_logger = logging.getLogger(__name__)


class SellerDetails(RedisCacheBase):
    def fetch_data(self, key: str):
        pass
