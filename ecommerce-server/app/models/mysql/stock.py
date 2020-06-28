# coding=utf-8
import logging

from app.models import db
from app.models.mysql.base import TimestampMixin

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


class Stock(db.Model, TimestampMixin):
    """
    Contain stock details of product
    """
    __tablename__ = 'stock'

    def __init__(self, **kwargs):
        for k, v in kwargs.items():
            setattr(self, k, v)

    sku = db.Column(db.String(30), primary_key=True)
    stock = db.Column(db.Integer, default=0)
    promotion_stock = db.Column(db.Integer, default=0)
    flash_sale_stock = db.Column(db.Integer, default=0)

    def to_dict(self):
        return {
            'sku': self.sku,
            'promotion_stock': self.promotion_stock,
            'flash_sale_stock': self.flash_sale_stock,
        }
