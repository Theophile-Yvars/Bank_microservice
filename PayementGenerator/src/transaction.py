import json
import random
import uuid
from dotenv import load_dotenv
import requests
from utils.CustomJSONEncoder import CustomEncoder
from dto.PayementDTO import PaymentDto

load_dotenv()

def createTransaction(country, clientID):
    idClient = clientID
    type = "Particulier"
    amount = round(random.uniform(1.0, 100.0), 2)
    origine = "Direct"
    idTargetClient = uuid.uuid4()
    return PaymentDto("auto_generated_name", idClient, amount, country, type, origine, idTargetClient)


def sendTrasaction(transaction):
    payment_json = json.dumps(transaction.__dict__, cls=CustomEncoder)
    print(payment_json)
    url = "http://localhost:8080/transaction/postTransaction"
    response = requests.post(url, json=payment_json)
    print(response)
    return response

    