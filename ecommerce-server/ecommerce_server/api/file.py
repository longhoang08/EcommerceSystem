# coding=utf-8
import logging

import flask_restplus as _fr
from flask import request

from ecommerce_server import services
from ecommerce_server.extensions import Namespace

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

ns = Namespace('file', description='File operations')


@ns.route('/store-image', methods=['POST'])
class StoreImage(_fr.Resource):
    def post(self):
        images = request.files
        return services.file.upload_image(images)
