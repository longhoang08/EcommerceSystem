# coding=utf-8
import logging

__author__ = 'LongHB'

from ecommerce_server.models.seller import Seller
from ecommerce_server import models as m

_logger = logging.getLogger(__name__)


def save(register: Seller) -> Seller:
    m.db.session.add(register)
    # m.db.session.commit()
    return register


def create_new_seller(**kwargs) -> Seller:
    register = Seller(**kwargs)
    return save(register)
