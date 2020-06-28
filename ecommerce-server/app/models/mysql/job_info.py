# coding=utf-8
import logging

from app.models import db
from app.models.mysql.base import TimestampMixin

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


class JobInfo():
    STOCK_INGESTION = 'stock_ingestion_timestamp'


class JobTime(db.Model, TimestampMixin):
    """
    Contain stock details of product
    """
    __tablename__ = 'job'

    def __init__(self, **kwargs):
        for k, v in kwargs.items():
            setattr(self, k, v)

    name = db.Column(db.String(30), primary_key=True)
    time = db.Column(db.TIMESTAMP)

    def to_dict(self):
        return {
            'name': self.name,
            'time': self.last_ingest_time
        }
