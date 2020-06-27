# coding=utf-8
import logging
from typing import List

from app.helpers.catalog.product_utils import Utilities
from app.repositories.mysql import seller as seller_repo

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def get_pending_seller(args) -> List[dict]:
    args = Utilities.reformat_search_text_search_params(args)
    sellers = seller_repo.find_pending_seller(args.get('_page'), args.get('_limit'))
    return [seller.to_dict() for seller in sellers]
