
from fastapi import FastAPI

from app.decision_tree.DecisionTree import DecisionTree
from app.kafka.kafka import Kafka

app = FastAPI()
decisionTree = DecisionTree()
kafka = Kafka()

@app.get("/marketing/analysis")
async def analysis():
    print("Activation of the decision tree algorithm ... ")
    await decisionTree.run()
    return {"status" : "starting"}

@app.get("/")
async def root():
    print("Marketing : Decision Tree, TEST 2")
    return {"Marketing": "Decision Tree"}

# Exemple de l'utilisation de la fonction de production
@app.get("/test/producer")
async def root():
    message = {"Marketing": "Test Offer"}
    await kafka.produce("primabankoffers", message)
    return {"Marketing": "Test producer"}