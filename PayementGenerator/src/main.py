import uuid
from utils.CustomParser import CustomParser
from transaction import createTransaction, sendTrasaction
from utils.validator import checkArgsValidity

parser = CustomParser()

country = parser.args.country
numberOfTransactions = int(parser.args.number)
try:
    clientID = int(parser.args.client)
except Exception:
    clientID = uuid.uuid4()

checkArgsValidity(parser.args)

for i in range(0, numberOfTransactions):
    transaction = createTransaction(country, clientID)
    sendTrasaction(transaction)
