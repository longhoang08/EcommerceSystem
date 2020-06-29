# coding=utf-8
import logging

from app.models import db

from app.models.mysql.base import TimestampMixin


class OrderProduct(db.Model, TimestampMixin):
    """
    Contains information of order and product
    """
    __tablename__ = 'order_product'

    def __init__(self, **kwargs):
        for k, v in kwargs.items():
            setattr(self, k, v)

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    order_id = db.Column(db.Integer)
    sku = db.Column(db.String(30))
