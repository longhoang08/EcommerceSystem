# coding=utf-8
import logging

__author__ = 'LongHB'

import re

_logger = logging.getLogger(__name__)


def normalize_phone_number(phone_number: str) -> str:
    if not phone_number: return phone_number
    if phone_number[0] == '+': phone_number = phone_number.replace('+', '', 1)
    if len(phone_number) >= 10 and len(phone_number) <= 11:
        if phone_number[0:2] == '84': phone_number = phone_number.replace('94', '0', 1)
    return phone_number


def normalize_text(text: str) -> str:
    return None if text is None else ' '.join(text.split()).lower()



def remove_vi_accent(s):
    """
    Convert an accent Vietnamese sentence to no accent string
    :param string s:
    :return: string s without accent
    """
    in_tab = "àáảãạăắằẵặẳâầấậẫẩđèéẻẽẹêềếểễệ" \
             "ìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ"
    out_tab = "aaaaaaaaaaaaaaaaadeeeeeeeeeee" \
              "iiiiiooooooooooooooooouuuuuuuuuuuyyyyy"
    en_query = s if not s else s.lower().translate(
        str.maketrans(in_tab, out_tab))
    return en_query if not en_query else re.sub(' +', ' ', en_query).strip()
