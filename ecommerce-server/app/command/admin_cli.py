# coding=utf-8
import logging

import click
from flask.cli import AppGroup

from app.repositories.es.backdoor import Ingestion, product_query_condition, migrate_product_data
from app.repositories.mysql import user as user_repo
from app.services import user as user_service

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

CONTEXT_SETTINGS = dict(help_option_names=['-h', '--help'])
admin_cli = AppGroup('admin', help="Backdoor by admin",
                     context_settings=CONTEXT_SETTINGS)


@admin_cli.command('set_password', help='Set user password')
@click.argument('email', metavar='{email}')
@click.argument('password', metavar='{password}')
def set_password(email, password):
    print("Setting password of user _{}_ to {}".format(email, password))
    user = user_repo.find_one_by_email(email)
    if not user:
        print("User not exist")
        return
    user_service.create_or_update_password(user, password)
    print("Done")


@admin_cli.command('migrate_product', help='Migrate stock & price data')
def migrate_stock():
    print("Start migrate stock data from es to mysql")
    ingestion = Ingestion('products', product_query_condition)
    ingestion.ingest_all(100, migrate_product_data)
