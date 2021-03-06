# coding=utf-8
import logging
from typing import List
import threading
from app.commons.decorators import user_required
from app.helpers import encode_token
from app.helpers.catalog.product_utils import Utilities
from app.models.mysql.order import OrderStatus
from app.repositories import file as file_repo
from app.repositories.mysql import order as repo, product_sql
from app.services.product_sql import get_order_stock_details
from app.services import order_product as order_product_service, product as product_service

__author__ = 'LongHB'

from config import SERVER_BASE_URL, TOKEN_UPTIME

_logger = logging.getLogger(__name__)

ORDER_FAILED_RESPONSE = {
    'status': 'failed',
    'message': 'SSome products are out of stock or out of sales. Please reload the order',
}


@user_required
def get_order_details(args, **kwargs):
    try:
        args = Utilities.reformat_search_text_search_params(args)
    except Exception:
        pass
    order_id = args.get('order_id')

    user = kwargs.get('user')
    user_id = user.id

    if order_id:
        order = repo.find_order_by_id_and_user_id(order_id, user_id)
        orders = [order] if order else []
        return {'orders': orders}

    status = args.get('status')
    page = args.get('_page')
    limit = args.get('_limit')
    if status:
        orders = repo.find_order_by_status(user_id, status, page, limit)
    else:
        orders = repo.find_all_order(user_id, page, limit)
    print([order.to_dict() for order in orders])
    return {'orders': [order.to_dict() for order in orders]}


def check_order(skus: List[str], **kwargs) -> [List[dict], float]:
    order_reponse, total_price, _, failed_skus = get_order_stock_details(skus)
    return {
        'prices': order_reponse,
        'total_price': total_price,
        'failed_skus': failed_skus
    }


@user_required
def create_order(skus: List[str], expected_price: float, **kwargs):
    from app.services import product_lock
    with product_lock:
        order_reponse, total_price, products, failed_skus = get_order_stock_details(skus)
        if total_price != expected_price or failed_skus:
            return ORDER_FAILED_RESPONSE
        for index, product in enumerate(products):
            product.stock -= 1
            if order_reponse[index].get('promotion_price') > 0:
                product.promotion_price -= 1
            product_sql.save(product)

    # update es stock in another thread
    t = threading.Thread(target=product_service.update_stocks, args=(products,))
    t.start()

    user = kwargs.get('user')
    return {
        'status': 'ok',
        'total_price': total_price,
        'prices': order_reponse,
        'order_id': create_new_sql_order(user.id, skus)
    }


def create_new_sql_order(user_id: int, skus: List[str]) -> int:
    order = repo.create_new_order(user_id=user_id, status=OrderStatus.Pending)
    order_id = order.id
    order_product_service.create_new_order(order_id, skus)
    return order_id


# payment ==============================================================================================================

def generate_payment_link(order_id: int):
    order_id_encoded = encode_token(str(order_id), int(TOKEN_UPTIME))
    return SERVER_BASE_URL + "order/confirm/" + order_id_encoded


def generate_qr_code(order_id: int):
    return file_repo.generate_qr_code(generate_payment_link(order_id))


def pay_a_order(order_id: int):
    order = repo.find_order_by_id(order_id)
    if not order: return "Order not exits"
    if order.status != OrderStatus.Pending:
        return "Your order have already paid"
    order.status = OrderStatus.Paid
    repo.save(order)
    return "Done"
