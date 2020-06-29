# coding=utf-8
import logging

from app import models as m

__author__ = 'LongHB'

from app.models.mysql.order_product import OrderProduct

_logger = logging.getLogger(__name__)


def save(order_product: OrderProduct) -> OrderProduct:
    m.db.session.add(order_product)
    # m.db.session.commit()
    return order_product


def create_new_order_product(**kwargs) -> OrderProduct:
    register = OrderProduct(**kwargs)
    return save(register)
