# coding=utf-8
import datetime

from ecommerce_server.constant import link
from ecommerce_server.models import db, TimestampMixin, UserBase


class User(db.Model, TimestampMixin, UserBase):
    """
    Contains information of users table
    """
    __tablename__ = 'users'

    def __init__(self, **kwargs):
        for k, v in kwargs.items():
            setattr(self, k, v)

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    avatar_url = db.Column(db.String(256), default=link.DEFAULT_AVATAR)
    role = db.Column(db.Integer, default=0)
    is_active = db.Column(db.Boolean, nullable=False, default=True)
    last_login = db.Column(db.TIMESTAMP, nullable=False, default=datetime.datetime.now)
    un_block_at = db.Column(db.TIMESTAMP, nullable=True)

    def to_dict(self):
        return {
            'id': self.id,
            'email': self.email,
            'fullname': self.fullname,
            'avatarUrl': self.avatar_url,
            'isAdmin': self.is_admin,
            'isActive': self.is_active,
            'password': self.password,
        }

    def to_display_dict(self):
        return {
            'email': self.email,
            'fullname': self.fullname,
            'avatar_url': self.avatar_url,
        }
