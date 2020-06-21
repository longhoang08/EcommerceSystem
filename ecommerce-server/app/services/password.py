from app.extensions.custom_exception import PasswordDiffException
from app.helpers import verify_password, hash_password
from app.repositories.mysql import password as repo


def validate_new_hash_password(user_id: int, new_password: str) -> bool:
    passwords = repo.find_all_password_by_user_id(user_id)
    for store_password in passwords:
        print(store_password.password)
        if verify_password(store_password.password, new_password): return False
    return True


def add_new_hashed_password(user_id: int, new_password: str) -> str:
    if not validate_new_hash_password(user_id, new_password): raise PasswordDiffException()
    hashed_password = hash_password(new_password)
    repo.add_new_hash_password(user_id, hashed_password)
    repo.delete_old_password(user_id)
    return hashed_password
