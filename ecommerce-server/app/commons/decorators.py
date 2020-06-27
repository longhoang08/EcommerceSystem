from functools import wraps

from flask_jwt_extended import jwt_required, get_jwt_identity

from app.extensions.custom_exception import InvalidTokenException, PermissionException
from app.models.mysql.user import UserRole
from app.services import user as user_service


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


def admin_required(func):
    @wraps(func)
    def wrap_func(*args, **kwargs):
        try:
            # Todo: check token from redis
            email = get_email_from_jwt_token()
            user = user_service.find_one_by_email(email)
            if not user: raise Exception()
            if user.role != UserRole.Admin: raise Exception()
            kwargs['user'] = user
        except:
            raise PermissionException("Admin Required")
        return func(*args, **kwargs)

    return wrap_func


def seller_required(func):
    @wraps(func)
    def wrap_func(*args, **kwargs):
        try:
            # Todo: check token from redis
            email = get_email_from_jwt_token()
            user = user_service.find_one_by_email(email)
            if not user: raise Exception()
            if user.role != UserRole.Seller: raise Exception()
            kwargs['user'] = user
        except:
            raise PermissionException("Seller Required")
        return func(*args, **kwargs)

    return wrap_func
