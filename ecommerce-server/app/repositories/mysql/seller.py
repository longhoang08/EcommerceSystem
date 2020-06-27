# coding=utf-8
import logging
from typing import List

from app import models as m
from app.models.mysql.seller import Seller, SellerStatus

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def save(register: Seller) -> Seller:
    m.db.session.add(register)
    # m.db.session.commit()
    return register


def create_new_seller(**kwargs) -> Seller:
    register = Seller(**kwargs)
    return save(register)


def find_pending_seller(page: int, limit: int) -> List[Seller]:
    sellers = Seller.query. \
        filter(Seller.status == SellerStatus.Pending). \
        order_by(Seller.created_at.asc()) \
        .limit(limit) \
        .offset((page - 1) * limit)
    return sellers
