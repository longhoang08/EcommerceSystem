# coding=utf-8
import logging

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def normalize_phone_number(phone_number: str) -> str:
    if not phone_number: return phone_number
    if phone_number[0] == '+': phone_number = phone_number.replace('+', '', 1)
    if len(phone_number) >= 10 and len(phone_number) <= 11:
        if phone_number[0:2] == '84': phone_number = phone_number.replace('94', '0', 1)
    return phone_number
