class AnalysisDTO:
    def __init__(self, id_client, product):
        self.id_client = id_client
        self.product = product

    def to_dict(self):
        return {
            'idClient': self.id_client,
            'product': self.product.to_dict() if self.product else None
        }

    def get_id_client(self):
        return self.id_client

    def set_id_client(self, id_client):
        self.id_client = id_client

    def get_product(self):
        return self.product

    def set_product(self, product):
        self.product = product

    def __eq__(self, other):
        if self is other:
            return True
        if not isinstance(other, AnalysisDTO):
            return False
        return (
            self.id_client == other.id_client
            and self.product == other.product
        )

    def __hash__(self):
        return hash((self.id_client, self.product))

    def __str__(self):
        return f"AnalysisDTO{{idClient={self.id_client}, product={self.product}}}"
