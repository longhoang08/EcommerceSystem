# coding=utf-8
import logging

from flask_mail import Message

from app.extensions.custom_exception import CantSendEmailException

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)


def send_email(subject, contact, body_message):
    from app.services import my_mail
    msg = Message(subject,
                  sender='viem.t.viemde@gmail.com',
                  recipients=[contact],
                  html=body_message)
    try:
        my_mail.send(msg)
    except:
        raise CantSendEmailException()
