# coding=utf-8
import logging
from typing import List

from ecommerce_server import models as m

__author__ = 'LongHB'

from ecommerce_server.models import Password

_logger = logging.getLogger(__name__)


def save(password: Password):
    m.db.session.add(password)
    # m.db.session.commit()
    return password


def save_historic_password_to_database(**kwargs) -> Password:
    password = m.Password(**kwargs)
    return save(password)


def delete_old_password(user_id: int) -> List[Password]:
    passwords = find_all_password_by_user_id(user_id)
    if len(passwords) > 5:
        m.db.session.delete(passwords[0])
        del passwords[0]
        # m.db.session.commit()
    return passwords


def add_new_hash_password(user_id: int, password_hash: str):
    save_historic_password_to_database(user_id=user_id, password=password_hash)


# ======================================================================================================================
def find_all_password_by_user_id(user_id: int) -> List[m.Password]:
    passwords = m.Password.query.filter(
        m.Password.user_id == user_id
    ).order_by(m.Password.created_at).all()
    return passwords
