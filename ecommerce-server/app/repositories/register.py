# coding=utf-8
import logging

from sqlalchemy import or_

from app import models as m
from app.models import Register

__author__ = 'LongHB'

_logger = logging.getLogger(__name__)


def save(register: Register) -> Register:
    m.db.session.add(register)
    # m.db.session.commit()
    return register


def create_new_register(**kwargs) -> Register:
    register = Register(**kwargs)
    return save(register)


def find_one_by_email_or_phone_number(email: str, phone_number: str) -> Register:
    register = Register.query.filter(
        or_(
            Register.phone_number == phone_number,
            Register.email == email
        )
    ).first()

    return register or None


def find_one_by_email(email: str) -> Register:
    register = Register.query.filter(
        Register.email == email
    ).first()

    return register or None


def find_one_by_phone_number(phone_number: str) -> Register:
    register = Register.query.filter(
        Register.phone_number == phone_number
    ).first()

    return register or None


def find_one_by_username(username: str) -> Register:
    register = Register.query.filter(
        Register.username == username
    ).first()
    return register or None


def delete_one_by_email(email: str):
    Register.query.filter(
        Register.email == email
    ).delete()
    m.db.session.commit()
