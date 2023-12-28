class ProductDTO:
    def __init__(self, id, name, description, price, mode):
        self.id = id
        self.name = name
        self.description = description
        self.price = price
        self.mode = mode

    def to_dict(self):
        return {
            'id': self.id,
            'name': self.name,
            'description': self.description,
            'price': self.price,
            'mode': self.mode
        }

    def get_id(self):
        return self.id

    def set_id(self, id):
        self.id = id

    def get_name(self):
        return self.name

    def set_name(self, name):
        self.name = name

    def get_description(self):
        return self.description

    def set_description(self, description):
        self.description = description

    def get_price(self):
        return self.price

    def set_price(self, price):
        self.price = price

    def get_mode(self):
        return self.mode

    def set_mode(self, mode):
        self.mode = mode

    def __eq__(self, other):
        if self is other:
            return True
        if not isinstance(other, ProductDTO):
            return False
        return (
            self.price == other.price
            and self.id == other.id
            and self.name == other.name
            and self.description == other.description
            and self.mode == other.mode
        )

    def __hash__(self):
        return hash((self.id, self.name, self.description, self.price, self.mode))

    def __str__(self):
        return f"ProductDTO{{id='{self.id}', name='{self.name}', description='{self.description}', price={self.price}, mode='{self.mode}'}}"
