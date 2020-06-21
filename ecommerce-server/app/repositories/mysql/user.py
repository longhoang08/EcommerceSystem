# coding=utf-8
import logging

from sqlalchemy import or_

from app import models as m
from app.models.mysql.user import User
from app.helpers import hash_password

__author__ = 'LongHB'

_logger = logging.getLogger(__name__)


def save(user: User) -> User:
    m.db.session.add(user)
    return user


def create_new_user(**kwargs) -> User:
    user = User(**kwargs)
    return save(user)


def change_password(user, new_password) -> User:
    password_hash = hash_password(new_password)
    user.password = password_hash
    return save(user)


def find_one_by_email_or_phone_number(email: str, phone_number: str) -> User:
    # Todo: normalize phone number and email
    user = User.query.filter(
        or_(
            User.phone_number == phone_number,
            User.email == email
        )
    ).first()

    return user or None


def find_one_by_user_id(user_id: int) -> User:
    user = User.query.filter(
        User.id == user_id
    ).first()

    return user or None


def find_one_by_email(email: str) -> User:
    user = User.query.filter(
        User.email == email
    ).first()

    return user or None


def find_one_by_phone_number(phone_number: str) -> User:
    user = User.query.filter(
        User.phone_number == phone_number
    ).first()

    return user or None
