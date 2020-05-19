from ecommerce_server.extensions.custom_exception import PasswordDiffException
from ecommerce_server.repositories import password as repo


def validate_new_hash_password(user_id: int, hashed_password: str) -> bool:
    passwords = repo.find_all_password_by_user_id(user_id)
    for store_password in passwords:
        if store_password == hashed_password: return False
    return True


def add_new_hashed_password(user_id: int, hashed_password: str):
    if not validate_new_hash_password(user_id, hashed_password): PasswordDiffException()
    repo.add_new_hash_password(user_id, hashed_password)
    repo.delete_old_password(user_id)
