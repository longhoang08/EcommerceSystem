import logging

from app.models import db
from app.models.mysql.base import TimestampMixin
from app.repositories.redis import user as user_cache

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


class Rating(db.Model, TimestampMixin):
    __tablename__ = 'rating'

    def __init__(self, **kwargs):
        for k, v in kwargs.items():
            setattr(self, k, v)

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    user_id = db.Column(db.Integer)
    sku = db.Column(db.String(30))
    star = db.Column(db.Integer)
    comment = db.Column(db.Text(1000))

    def to_dict(self):
        return {
            'user_name': user_cache.get_user_name_from_cache(self.user_id),
            'star': self.star,
            'comment': self.comment,
            'created_at': self.created_at,
        }
