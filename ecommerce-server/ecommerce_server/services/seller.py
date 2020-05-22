# coding=utf-8
import logging

__author__ = 'LongHB'

from ecommerce_server import ForbiddenException
from ecommerce_server.commons.decorators import login_required
from ecommerce_server.models import UserRole
from ecommerce_server.models.seller import SellerStatus, Seller
from ecommerce_server.services import user as user_service
from ecommerce_server.repositories import seller as repo

_logger = logging.getLogger(__name__)


@login_required
def register_to_be_seller(description, **kwargs) -> Seller:
    email = kwargs.get('email')
    user = user_service.find_one_by_email_else_throw(email)
    if user.role != UserRole.Customer: raise ForbiddenException("You must be customer to request to be new seller")
    seller = repo.create_new_seller(user_id=user.id, description=description)
    return repo.save(seller)
