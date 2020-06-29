# coding=utf-8
import logging

from app.repositories.redis import RedisCacheBase
from app.repositories.mysql import rating as rating_repo

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


class RatingCache(RedisCacheBase):

    def __init__(self):
        super().__init__("rating", ttl=300)

    def fetch_data(self, key: str):  # key is sku of products
        return rating_repo.get_rating_by_sku(key)
