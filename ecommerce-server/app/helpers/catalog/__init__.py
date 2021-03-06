# coding=utf-8
import json
import logging

__author__ = 'LongHB'

import os
from typing import Dict, List

_logger = logging.getLogger(__name__)

cat_id_to_code = {}
_leaf_categories_ids = []
all_categories_from_code = {}
all_leaf_categories = []


# categories
def _get_category_by_id(category_id: int) -> Dict:
    return categories_data[cat_id_to_code[category_id]]


with open(os.path.join(os.path.dirname(__file__), 'categories.json')) as json_file:
    is_leaf_category = {}
    categories_data = json.load(json_file)

    # Init add category id
    for category_code in categories_data.keys():
        category = categories_data[category_code]
        category_id = category["id"]
        is_leaf_category[category_id] = True
        cat_id_to_code[category_id] = category_code

    for category_code in categories_data.keys():
        category = categories_data[category_code]
        is_leaf_category[category["parent_id"]] = False

    _leaf_categories_ids = [category_id for category_id in is_leaf_category.keys() if is_leaf_category[category_id]]

    for category_id in _leaf_categories_ids:
        category_code = cat_id_to_code[category_id]
        current_category = categories_data[category_code]
        all_leaf_categories.append({
            'code': current_category['code'],
            'name': current_category['name']
        })
        all_parrent_categories = [current_category]
        while current_category["parent_id"] != 0:
            current_category = _get_category_by_id(current_category["parent_id"])
            all_parrent_categories.append(current_category)
        all_categories_from_code[category_code] = all_parrent_categories

# read brand data
with open(os.path.join(os.path.dirname(__file__), 'brands.json')) as json_file:
    brands_data = json.load(json_file)


def is_valid_brand_code(brand_code: str) -> bool:
    return brand_code in brands_data


def is_valid_category_code(category_code: str) -> bool:
    return category_code in all_categories_from_code


def get_brand_from_code(brand_code: str) -> dict:
    return brands_data.get(brand_code) or {}


def get_categories_from_category_code(category_code: str) -> List[dict]:
    return all_categories_from_code.get(category_code) or []
