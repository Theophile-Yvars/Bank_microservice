from dataclasses import dataclass
import uuid


@dataclass
class PaymentDto:
    name: str
    account: uuid.UUID
    price: float
    country: str  # France, Suisse, Wakanda
    type: str  # Entreprise ou Particulier
    origine: str  # Internet ou Direct
    targetAccount: uuid.UUID  # Note: corrected typo in variable name

def __init__(self, name, account, price, country, type, origine, targetAccount):
        self.name = name
        self.account = account
        self.price = price
        self.country = country
        self.type = type
        self.origine = origine
        self.targetAccount = targetAccount
