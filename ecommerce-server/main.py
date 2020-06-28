# coding=utf-8
import logging

import time

from apscheduler.schedulers.background import BackgroundScheduler
from app import app

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def print_date_time():
    print(time.strftime("%A, %d. %B %Y %I:%M:%S %p"))


# register for jobs
scheduler = BackgroundScheduler()
scheduler.add_job(func=print_date_time, trigger="interval", seconds=60)
scheduler.start()

if __name__ == '__main__':
    app.run()
