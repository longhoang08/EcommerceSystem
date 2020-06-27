# coding=utf-8
import logging
from typing import List

from app.commons.decorators import admin_required
from app.helpers.catalog.product_utils import Utilities
from app.models.mysql.seller import SellerStatus
from app.repositories.mysql import seller as seller_repo

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


@admin_required
def get_pending_seller(args) -> List[dict]:
    args = Utilities.reformat_search_text_search_params(args)
    sellers = seller_repo.find_pending_seller(args.get('_page'), args.get('_limit'))
    return [seller.to_dict() for seller in sellers]


@admin_required
def confirm_seller_req(ids: List[int], **kwargs):
    sellers = seller_repo.find_pending_seller_by_id(ids)
    if not sellers: return "Can't confirm for any sellers"
    message = "Confirmed for sellers with ids: "
    new_ids = []
    for seller in sellers:
        seller.status = SellerStatus.Approved
        seller_repo.save(seller)
        new_ids.append(str(seller.user_id))
    message += ','.join((new_ids))
    return message
