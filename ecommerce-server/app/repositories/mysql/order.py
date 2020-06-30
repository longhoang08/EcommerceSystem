# coding=utf-8
import logging
import logging
from typing import List

from app import models as m

__author__ = 'LongHB'

from app.models.mysql.order import Order, OrderStatus

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


def find_order_by_id_and_user_id(id: int, user_id: int) -> Order:
    order = Order.query.filter(
        Order.id == id,
        Order.user_id == user_id
    ).first()
    return order or None


def find_order_by_status(user_id: int, status: int, page: int, limit: int) -> List[Order]:
    orders = Order.query. \
        filter(Order.status == status, Order.user_id == user_id). \
        order_by(Order.created_at.desc()) \
        .limit(limit) \
        .offset((page - 1) * limit) \
        .all()

    return orders


def find_all_order(user_id: int, page: int, limit: int) -> List[Order]:
    orders = Order.query \
        .filter(Order.user_id == user_id) \
        .order_by(Order.created_at.desc()) \
        .limit(limit) \
        .offset((page - 1) * limit) \
        .all()
    return orders
