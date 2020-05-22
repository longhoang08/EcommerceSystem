from ecommerce_server.models import db, TimestampMixin, UserBase


class Register(db.Model, TimestampMixin, UserBase):
    __tablename__ = 'register'

    def __init__(self, **kwargs):
        for k, v in kwargs.items():
            setattr(self, k, v)

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)

    def to_dict(self):
        return {
            'email': self.email,
            'fullname': self.fullname,
            'password': self.password,
            'created_at': self.created_at,
        }

    def to_display_dict(self):
        return {
            'username': self.username,
            'email': self.email,
            'fullname': self.fullname,
        }
