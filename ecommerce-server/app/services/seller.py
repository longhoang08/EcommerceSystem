# coding=utf-8
import logging

__author__ = 'LongHB'

from app import ForbiddenException
from app.commons.decorators import login_required, seller_required
from app.models.mysql.seller import Seller
from app.models.mysql.user import UserRole
from app.repositories.redis.product import ProductCache
from app.services import user as user_service, product as product_service
from app.repositories.mysql import seller as repo

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


@seller_required
def upsert_product(sku, brand_code, categories_code, name, price, **kwargs) -> dict:
    product = product_service.find_by_sku(sku)
    seller = kwargs.get('user')


# ======================================================================================================================

def _parse_categories(data: dict):
    pass


def _parse_branch(data: dict):
    pass


def _parse_images(data: dict):
    pass


def _parse_stock(data: dict):
    pass
