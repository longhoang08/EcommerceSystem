# coding=utf-8
import logging

from app import models as m
from app.models.mysql.seller import Seller

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def save(register: Seller) -> Seller:
    m.db.session.add(register)
    # m.db.session.commit()
    return register


def create_new_seller(**kwargs) -> Seller:
    register = Seller(**kwargs)
    return save(register)
