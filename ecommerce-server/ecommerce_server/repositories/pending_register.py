# coding=utf-8
import logging

from sqlalchemy import or_

from ecommerce_server import models

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def save_pending_register_to_database(**kwargs) -> models.Register:
    register = models.Register(**kwargs)
    models.db.session.add(register)
    models.db.session.commit()
    return register


def find_one_by_email_or_phone_number(email: str, phone_number: str) -> models.Register:
    register = models.Register.query.filter(
        or_(
            models.Register.phone_number == phone_number,
            models.Register.email == email
        )
    ).first()

    return register or None


def find_one_by_email(email) -> models.Register:
    register = models.Register.query.filter(
        models.Register.email == email
    ).first()

    return register or None


def find_one_by_username(username) -> models.Register:
    register = models.Register.query.filter(
        models.Register.username == username
    ).first()

    return register or None


def delete_one_by_email(email):
    models.Register.query.filter(
        models.Register.email == email
    ).delete()
    models.db.session.commit()
