# coding=utf-8
import logging

from app.models import db
from app.models.mysql.base import TimestampMixin

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


class OrderStatus():
    Pending = 0
    Paid = 1
    Finished = 2


class Order(db.Model, TimestampMixin):
    """
    Contains information of order
    """
    __tablename__ = 'order'

    def __init__(self, **kwargs):
        for k, v in kwargs.items():
            setattr(self, k, v)

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    user_id = db.Column(db.Integer)
    status = db.Column(db.Integer, default=OrderStatus.Pending)

    def to_dict(self):
        return {
            'id': self.user_id,
            'status': 'pending' if self.status == OrderStatus.Pending \
                else 'paid' if self.status == OrderStatus.Paid \
                else 'finished'
        }
