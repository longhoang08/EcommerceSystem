# coding=utf-8
import logging

__author__ = 'LongHB'

from app.models.seller import Seller
from app import models as m

_logger = logging.getLogger(__name__)


def save(register: Seller) -> Seller:
    m.db.session.add(register)
    # m.db.session.commit()
    return register


def create_new_seller(**kwargs) -> Seller:
    register = Seller(**kwargs)
    return save(register)
