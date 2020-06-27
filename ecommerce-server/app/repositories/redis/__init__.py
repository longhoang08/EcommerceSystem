# coding=utf-8

import logging

from abc import ABC, abstractmethod
from app.repositories import redis_client

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

KEY_SPLITTER = ":"
NONE_VALUE_TO_CACHE = b''


class RedisCacheBase(ABC):
    """
    ABC means this class is abstract class
    """

    def __init__(self, name: str, ttl: int = None):
        """
        :param name: The name of the data is cached
        :param ttl: time-to-live in second of caching data. Default to None -> No expired time
        """

        self.name = name
        self.ttl = ttl
        self.redis_client = redis_client

    def _get_full_key(self, key: str) -> str:
        return self.name + KEY_SPLITTER + key

    def get(self, key: str):
        if key is None:
            return None
        full_key = self._get_full_key(key)
        value = self.redis_client.get(full_key)
        if value == NONE_VALUE_TO_CACHE: return None
        value = value.decode("utf-8") if value else value
        if not value:
            value = self.fetch_data(key)
            redis_client.set(full_key, NONE_VALUE_TO_CACHE, ex=self.ttl) if not value else \
                redis_client.set(full_key, value, ex=self.ttl)
        return value

    @abstractmethod
    def fetch_data(self, key: str):
        raise NotImplemented
