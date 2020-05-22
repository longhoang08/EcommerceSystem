from functools import wraps

from flask_jwt_extended import jwt_required, get_jwt_identity

from ecommerce_server.extensions.custom_exception import InvalidTokenException


@jwt_required
def get_email_from_jwt_token():
    email = get_jwt_identity()
    return email


# use for api required login and set email to kwargs
def login_required(func):
    @wraps(func)
    def wrap_func(*args, **kwargs):
        try:
            # Todo: check token from redis
            kwargs['email'] = get_email_from_jwt_token()
        except:
            raise InvalidTokenException("Invalid token or Signal expired")
        return func(*args, **kwargs)

    return wrap_func
