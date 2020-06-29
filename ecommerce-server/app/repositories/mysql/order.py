# coding=utf-8
import logging
import logging
from typing import List

from app import models as m

__author__ = 'LongHB'

from app.models.mysql.order import Order

_logger = logging.getLogger(__name__)


def save(order: Order) -> Order:
    m.db.session.add(order)
    m.db.session.commit()
    return order


def create_new_order(**kwargs) -> Order:
    register = Order(**kwargs)
    return save(register)


def find_order_by_id(id: int) -> Order:
    order = Order.query.filter(
        Order.id == id
    ).first()
    return order or None
