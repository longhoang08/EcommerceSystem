# coding=utf-8
import logging

__author__ = 'LongHB'

from werkzeug.datastructures import MultiDict

from ecommerce_server.repositories import file as repo

_logger = logging.getLogger(__name__)


def upload_image(images: MultiDict):
    return {
        'image-url': repo.upload_image(images)
    }
