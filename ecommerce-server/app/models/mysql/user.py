# coding=utf-8
import datetime
import enum

from app.commons.uet_constant import DEFAULT_AVATAR
from app.models import db
from app.models.mysql.base import TimestampMixin, UserBase


class UserRole():
    Admin = 0
    Customer = 1
    Seller = 2


class User(db.Model, TimestampMixin, UserBase):
    """
    Contains information of users table
    """
    __tablename__ = 'user'

    def __init__(self, **kwargs):
        for k, v in kwargs.items():
            setattr(self, k, v)

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    avatar_url = db.Column(db.String(256), default=DEFAULT_AVATAR)
    role = db.Column(db.Integer, default=UserRole.Customer)
    is_active = db.Column(db.Boolean, nullable=False, default=True)
    last_login = db.Column(db.TIMESTAMP, nullable=False, default=datetime.datetime.now)
    un_block_at = db.Column(db.TIMESTAMP, nullable=True)

    def to_dict(self):
        return {
            'id': self.id,
            'email': self.email,
            'fullname': self.fullname,
            'avatar_url': self.avatar_url,
            'gender': "Male" if self.gender else "Female",
            'address': self.address,
        }

    def to_display_dict(self):
        return {
            'id': self.id,
            'email': self.email,
            'fullname': self.fullname,
            'avatar_url': self.avatar_url,
            'gender': "Male" if self.gender else "Female",
            'address': self.address,
            'role': "seller" if self.role == UserRole.Seller else "customer" if self.role == UserRole.Customer else 'admin'
        }
