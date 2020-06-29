import datetime
import os
from datetime import datetime, timezone

from flask import g

ISO_DATETIME_FORMAT = '%Y-%m-%dT%H:%M:%SZ'


def get_expired_time():
    return (datetime.datetime.now() +
            datetime.timedelta(minutes=int(os.environ['TOKEN_UPTIME'])))


# max age in seconds
def get_max_age():
    return int(os.environ['TOKEN_UPTIME']) * 60


def minutes_to_ms(minutes):
    return int(minutes) * 60000


def get_time_range_to_block():
    return int(os.environ['TIME_RANGE_TO_BLOCK']) * 60


def get_current_timestamp() -> float:
    return datetime.datetime.now().timestamp()


def get_utc_timestamp():
    utc_timestamp = g.utc_timestamp if hasattr(g, 'utc_timestamp') else int(datetime.utcnow().timestamp())
    return utc_timestamp


def date_time_to_iso_string(date_local=None):
    if not date_local: date_local = datetime.now()
    date_utc = date_local.astimezone(timezone.utc)
    return date_utc.strftime(ISO_DATETIME_FORMAT)
