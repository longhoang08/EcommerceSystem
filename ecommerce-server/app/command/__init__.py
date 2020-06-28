# coding=utf-8
import logging

__author__ = 'LongHB'

from app.command.admin_cli import admin_cli

_logger = logging.getLogger(__name__)


def init_command(app, **kwargs):
    """
    :param flask.Flask app: the app
    :param kwargs:
    :return:
    """

    app.cli.add_command(admin_cli)
