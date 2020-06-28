# coding=utf-8
import logging

from sqlalchemy import func

from app.models.mysql.job_info import JobTime
from app import models as m

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def save(job: JobTime, commit=False):
    m.db.session.add(job)
    if commit:
        m.db.session.commit()
    return job


def save_job_to_database(**kwargs) -> JobTime:
    job = JobTime(**kwargs)
    return save(job)


def upsert_job_to_database(job_name) -> JobTime:
    job_time = get_job_by_name(job_name)
    if not job_time:
        return save_job_to_database(name=job_time, time=func.now())
    job_time.last_ingest_time = func.now()
    return save(job_time)


def get_job_by_name(job_name) -> JobTime:
    job_time = JobTime.query.filter(
        JobTime.name == job_name
    ).first()
    return job_time if job_time else None
