import logging

from app.helpers.password import hash_password

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

VALID_USERS = [
    {
        "email": "test01@coin-mail.com",
        "username": "account1",
        "fullname": "Account one",
        "password": "12345678"
    },
    {
        "email": "test02@testmail.com",
        "username": "account2",
        "fullname": "Account two",
        "password": "12345678"
    },
    {
        "email": "test03@testmail.com",
        "username": "account3",
        "fullname": "Account three",
        "password": "12345678"
    }
]


def insert_some_user_to_db_for_testing():
    for register in VALID_USERS:
        app.repositories.mysql.user.create_new_user(
            username=register['username'],
            email=register['email'],
            fullname=register['fullname'],
            password=hash_password(register['password']),
        )


def insert_some_register_to_db_for_testing():
    for register in VALID_USERS:
        app.repositories.mysql.register.create_new_register(**register)
