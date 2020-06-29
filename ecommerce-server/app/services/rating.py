# coding=utf-8
import logging

from app import BadRequestException
from app.commons.decorators import login_required, user_required
from app.helpers.catalog.product_utils import Utilities
from app.repositories.mysql import rating as repo

__author__ = 'LongHB'

from app.repositories.redis.product import RatingCache

_logger = logging.getLogger(__name__)


@user_required
def create_rating(star: int, sku: str, **kwargs):
    _rating_check(star)
    user = kwargs.get('user')
    comment = kwargs.get('comment', '')
    return repo.create_new_rating(user_id=user.id, comment=comment, sku=sku, star=star)


def find_rating(sku, **kwargs):
    args = Utilities.reformat_search_text_search_params(kwargs)
    star = args.get('star')
    if star:
        _rating_check(star)
        ratings = repo.find_all_rating_by_star(star, args.get('_page'), args.get('_limit'))
    else:
        ratings = repo.find_all_rating(sku, args.get('_page'), args.get('_limit'))
    ratings = [rating.to_dict() for rating in ratings]
    return {'ratings': ratings, 'total_rating': get_total_rating(sku)}


def get_total_rating(sku: str):
    rating_cache = RatingCache()
    return rating_cache.get(sku)


def _rating_check(star: int):
    if star < 1 or star > 5:
        raise BadRequestException("Star must be between 1 and 5")
