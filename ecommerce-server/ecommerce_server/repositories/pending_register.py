# coding=utf-8
import logging

from sqlalchemy import or_

from ecommerce_server import models

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def save_pending_register_to_database(**kwargs):
    pending_register = models.Register(**kwargs)
    models.db.session.add(pending_register)

    return pending_register


def find_one_by_email_or_username(email, username):
    pending_register = models.Register.query.filter(
        or_(
            models.Register.username == username,
            models.Register.email == email
        )
    ).first()

    return pending_register or None


def find_one_by_email(email):
    pending_register = models.Register.query.filter(
        models.Register.email == email
    ).first()

    return pending_register or None


def find_one_by_username(username):
    pending_register = models.Register.query.filter(
        models.Register.username == username
    ).first()

    return pending_register or None


def delete_one_by_email(email):
    models.Register.query.filter(
        models.Register.email == email
    ).delete()
    models.db.session.commit()
