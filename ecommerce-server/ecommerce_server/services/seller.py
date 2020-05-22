# coding=utf-8
import logging

__author__ = 'LongHB'

from ecommerce_server.commons.decorators import login_required

_logger = logging.getLogger(__name__)


@login_required
def register_to_be_seller(description, **kwargs):
    email = kwargs.get('email')
