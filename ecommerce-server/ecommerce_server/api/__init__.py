# coding=utf-8
import logging

from flask import Blueprint
from flask_restplus import Api

from ecommerce_server.extensions.exceptions import global_error_handler

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

api_bp = Blueprint('api', __name__, url_prefix='/api')

api = Api(
    app=api_bp,
    version='1.0',
    title='File Management API',
    validate=False,
)


def init_app(app, **kwargs):
    """
    :param flask.Flask app: the app
    :param kwargs:
    :return:
    """
    from .register import ns as register_ns
    from .user import ns as user_ns
    from .profile import ns as profile_ns

    api.add_namespace(register_ns)
    api.add_namespace(user_ns)
    api.add_namespace(profile_ns)

    app.register_blueprint(api_bp)
    api.error_handlers[Exception] = global_error_handler


from .schema import requests, responses