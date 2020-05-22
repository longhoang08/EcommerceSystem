# coding=utf-8
import enum

from ecommerce_server.models import db, TimestampMixin


class SellerStatus(enum.Enum):
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
