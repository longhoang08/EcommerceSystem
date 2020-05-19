# coding=utf-8
import logging

import sqlalchemy as _sa
from sqlalchemy import func
from sqlalchemy.ext.declarative import declared_attr

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


class TimestampMixin(object):
    """
    Adds `created_at` and `updated_at` common columns to a derived
    declarative model.
    """

    @declared_attr
    def created_at(self):
        return _sa.Column(_sa.TIMESTAMP,
                          server_default=func.now(), default=func.now(),
                          nullable=False)

    @declared_attr
    def updated_at(self):
        return _sa.Column(_sa.TIMESTAMP, server_default=func.now(),
                          default=func.now(),
                          nullable=False, onupdate=func.now())


class UserBase():
    @declared_attr
    def email(self):
        return _sa.Column(_sa.String(256), unique=True, nullable=False)

    @declared_attr
    def fullname(self):
        return _sa.Column(_sa.String(256))

    @declared_attr
    def phone_number(self):
        return _sa.Column(_sa.String(256), index=True, unique=True, nullable=True)

    @declared_attr
    def password(self):
        return _sa.Column(_sa.String(256))
