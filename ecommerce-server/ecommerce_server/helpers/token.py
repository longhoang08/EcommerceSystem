import datetime

import jwt

from ecommerce_server.helpers import get_environ

SECRET_KEY = get_environ('SECRET_KEY')


def encode_token(email, minute) -> str:
    from ecommerce_server.extensions.custom_exception import EncodeErrorException

    try:
        payload = {
            'exp': datetime.datetime.utcnow() + datetime.timedelta(minutes=minute),
            'email': email,
        }
        return jwt.encode(
            payload,
            SECRET_KEY,
            algorithm='HS256'
        ).decode("utf-8")
    except Exception as e:
        raise EncodeErrorException()


def decode_token(auth_token: str) -> str:
    from ecommerce_server.extensions.custom_exception import InvalidTokenException
    try:
        payload = jwt.decode(auth_token, SECRET_KEY)
        return payload['email']
    except:
        raise InvalidTokenException()
