# coding=utf-8
import logging

__author__ = 'LongHB'

from app.repositories.redis import RedisCacheBase
from app.repositories.mysql import user as repo

_logger = logging.getLogger(__name__)


def get_user_name_from_cache(user_id: int):
    user_cache = UserCache()
    return user_cache.get(str(user_id))


class UserCache(RedisCacheBase):
    def __init__(self):
        super().__init__("user::name", 3600)

    def fetch_data(self, key: str):
        user = repo.find_one_by_user_id(int(key))
        name = user.fullname if user else ''
        return name
