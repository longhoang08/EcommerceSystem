# coding=utf-8
import enum

from ecommerce_server.models import db, TimestampMixin


class SellerStatus():
    Pending = 0
    Approved = 1


class Seller(db.Model, TimestampMixin):
    """
    Contains information of users table
    """
    __tablename__ = 'seller'

    def __init__(self, **kwargs):
        for k, v in kwargs.items():
            setattr(self, k, v)

    user_id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    description = db.Column(db.Text(4294000000))
    status = db.Column(db.Integer, default=SellerStatus.Pending)

    def to_dict(self):
        return {
            'id': self.user_id,
            'description': self.email,
            'status': 'reviewing' if (self.status == SellerStatus.Pending) else 'approved',
        }
