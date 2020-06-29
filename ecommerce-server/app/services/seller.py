# coding=utf-8
import logging

__author__ = 'LongHB'

from app import ForbiddenException
from app.commons.decorators import login_required, seller_required
from app.extensions.custom_exception import PermissionException
from app.helpers.catalog import get_brand_from_code, get_categories_from_category_code
from app.helpers.catalog.product_utils import Converter
from app.models.mysql.seller import Seller
from app.models.mysql.user import UserRole, User
from app.services import user as user_service, product as product_service, ingestion as ingestion_service
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
def upsert_product(sku, brand_code, category_code, name, price, **kwargs) -> dict:
    stored_product = product_service.find_by_sku(sku)
    seller = kwargs.get('user')

    # validate permission
    _validate_permission_of_seller(stored_product, seller)

    product = {}
    product['name'] = name
    product['prices'] = {'price': price, 'price_sortable': price}
    product['sku'] = sku
    product['brand'] = get_brand_from_code(brand_code)
    product['categories'] = get_categories_from_category_code(category_code)
    product['images'] = _parse_images(kwargs.get('images_url', []))

    Converter.reformat_product(product)
    # if (kwargs.get(stock_changed))

    return ingestion_service.upsert_product(product)


def _validate_permission_of_seller(product: dict, seller: User):
    if product:
        seller_id = product.get('seller').get('id')
        if seller_id != seller.id:
            print(seller_id)
            print(seller.id)
            raise PermissionException("You can't change product info of other seller")


# ======================================================================================================================
def _parse_images(images_url: []):
    return [{'url': url} for url in images_url]


def _parse_stock(data: dict):
    pass
