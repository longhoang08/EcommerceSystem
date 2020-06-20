# coding=utf-8
import logging

__author__ = 'LongHB'

from app import ForbiddenException
from app.commons.decorators import login_required
from app.models import UserRole
from app.models.seller import Seller
from app.services import user as user_service
from app.repositories import seller as repo

_logger = logging.getLogger(__name__)


@login_required
def register_to_be_seller(description, **kwargs) -> Seller:
    email = kwargs.get('email')
    user = user_service.find_one_by_email_else_throw(email)
    if user.role != UserRole.Customer:
        raise ForbiddenException("You must be customer to request to be new seller")

    user_service.change_role_to_seller(user)
    seller = repo.create_new_seller(user_id=user.id, description=description)

    return repo.save(seller)
