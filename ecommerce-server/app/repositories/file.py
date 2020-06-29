# coding=utf-8
import logging

__author__ = 'LongHB'

from io import BytesIO

import qrcode
import requests
from flask import send_file
from werkzeug.datastructures import MultiDict

from config import IMG_CLIENT_ID
from app.extensions.custom_exception import CannotUploadPictureException

_logger = logging.getLogger(__name__)


def upload_image(images: MultiDict) -> str:
    headers = {'Authorization': 'Client-ID ' + IMG_CLIENT_ID}
    try:
        print(images)
        temp = images.get('image')
        print(temp)
        print(type(temp))
        print(type(images.get('image')))
        r = requests.post('https://api.imgur.com/3/upload', headers=headers, files=images)
        response = r.json()
        return response.get('data').get('link')
    except:
        raise CannotUploadPictureException()


def generate_qr_code(data: str) -> str:
    qr = qrcode.QRCode(
        version=1,
        error_correction=qrcode.constants.ERROR_CORRECT_L,
        box_size=10,
        border=4,
    )
    qr.add_data(data)
    qr.make(fit=True)
    img = qr.make_image(fill_color="black", back_color="white")
    img_io = BytesIO()
    img.get_image().save(img_io, 'JPEG', quality=70)
    img_io.seek(0)
    return send_file(img_io, mimetype='image/jpeg')
