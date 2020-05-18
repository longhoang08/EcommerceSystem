# coding=utf-8
import logging

import flask_bcrypt as _fb
import flask_migrate as _fm
import flask_sqlalchemy as _fs
from dotenv import load_dotenv
from flask_redis import FlaskRedis
from mockredis import MockRedis

from ecommerce_server.helpers.env import get_environ

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

from config import _DOT_ENV_PATH

load_dotenv(_DOT_ENV_PATH)

db = _fs.SQLAlchemy()
migrate = _fm.Migrate(db=db)
bcrypt = _fb.Bcrypt()
redis_client = FlaskRedis()


def init_app(app, **kwargs):
    db.app = app
    db.init_app(app)
    migrate.init_app(app)
    _logger.info('Start app in {env} environment with database: {db}'.format(
        env=app.config['ENV_MODE'],
        db=app.config['SQLALCHEMY_DATABASE_URI']
    ))
    redis_client.init_app(app)


from .base import TimestampMixin, UserBase
from .user import User
from .register import Register
from .password import Password