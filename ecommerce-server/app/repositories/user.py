# coding=utf-8
import logging

from sqlalchemy import or_

from app import models as m
from app.helpers import hash_password

__author__ = 'LongHB'

from app.models import User

_logger = logging.getLogger(__name__)


def save(user: User) -> User:
    m.db.session.add(user)
    # m.db.session.commit()
    return user


def create_new_user(**kwargs) -> User:
    user = m.User(**kwargs)
    return save(user)


def change_password(user, new_password) -> User:
    password_hash = hash_password(new_password)
    user.password = password_hash
    return save(user)


def find_one_by_email_or_phone_number(email: str, phone_number: str) -> m.User:
    # Todo: normalize phone number and email
    user = m.User.query.filter(
        or_(
            m.User.phone_number == phone_number,
            m.User.email == email
        )
    ).first()

    return user or None


def find_one_by_user_id(user_id: int) -> User:
    user = m.User.query.filter(
        m.User.id == user_id
    ).first()

    return user or None


def find_one_by_email(email: str) -> User:
    user = m.User.query.filter(
        m.User.email == email
    ).first()

    return user or None


def find_one_by_phone_number(phone_number: str) -> User:
    user = m.User.query.filter(
        m.User.phone_number == phone_number
    ).first()

    return user or None
