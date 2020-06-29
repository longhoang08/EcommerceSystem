# coding=utf-8
import logging
from typing import List

from sqlalchemy.sql import func

from app import models as m, BadRequestException

__author__ = 'LongHB'

from app.models.mysql.order import Order
from app.models.mysql.rating import Rating

_logger = logging.getLogger(__name__)


def save(rating: Rating) -> Order:
    m.db.session.add(rating)
    # m.db.session.commit()
    return rating


def create_new_rating(**kwargs) -> Rating:
    rating = Rating(**kwargs)
    return save(rating)


def find_by_id(id: int) -> Rating:
    rating = Rating.query.filter(
        Rating.id == id
    ).first()
    return rating or None


def find_all_rating(sku: str, page: int, limit: int) -> List[Rating]:
    print(sku)
    print(page)
    print(limit)
    ratings = Rating.query \
        .filter(Rating.sku == sku) \
        .order_by(Rating.created_at.desc()) \
        .limit(limit) \
        .offset((page - 1) * limit) \
        .all()
    return ratings


def find_all_rating_by_star(sku: str, star: int, page: int, limit: int) -> List[Rating]:
    ratings = Rating.query. \
        filter(Rating.star == star, Rating.sku == sku) \
        .order_by(Rating.created_at.desc()) \
        .limit(limit) \
        .offset((page - 1) * limit) \
        .all()
    return ratings


def get_rating_by_sku(sku: str):
    res = m.db.session.query(func.avg(Rating.star).label('average')).filter(Rating.sku == sku).first()
    return extract_result(res)


def extract_result(result):
    try:
        return float(result._asdict().get('average'))
    except Exception:
        return 0
