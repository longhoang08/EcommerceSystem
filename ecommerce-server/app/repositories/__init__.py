# coding=utf-8
import logging
import redis
from config import REDIS_URL

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

redis_client = redis.Redis.from_url(REDIS_URL)
