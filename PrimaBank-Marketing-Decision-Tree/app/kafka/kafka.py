import asyncio
import json
from aiokafka import AIOKafkaProducer
from app.dto.analyseDto import AnalysisDTO
from app.dto.productDto import ProductDTO
import re

class Kafka():
    def __init__(self):
        self.__loop = asyncio.get_event_loop()
        self.__kafka_bootstrap_servers: str = "kafka:9092"
        print("Kafka Init", flush=True)

    #def __kafka_serializer(self, value):
    #    return json.dumps(value).encode()

    def __kafka_serializer(self, value):
        if isinstance(value, (AnalysisDTO, ProductDTO)):
            return json.dumps(value.to_dict()).encode()
        return json.dumps(value).encode()

    def __kafka_serializer(self, value):
        analysis_pattern = re.compile(
            "AnalysisDTO\\{idClient='(\\d+)', product=ProductDTO\\{id='(\\d+)', name='(.*?)', description='(.*?)', price=(\\d+\\.\\d+), mode='(.*?)'}}")

        if isinstance(value, AnalysisDTO):
            formatted_string = f"AnalysisDTO{{idClient='{value.id_client}', product=ProductDTO{{id='{value.product.id}', name='{value.product.name}', description='{value.product.description}', price={value.product.price}, mode='{value.product.mode}'}}}}"
            if analysis_pattern.fullmatch(formatted_string):
                return formatted_string.encode()

        return json.dumps(value, default=self.json_default).encode()

    async def produce(self, topic: str, message):
        print("message : ", message, flush=True)

        producer = AIOKafkaProducer(
            loop=self.__loop,
            bootstrap_servers=self.__kafka_bootstrap_servers,
            value_serializer=self.__kafka_serializer,
        )

        try:
            print("Start producer")
            await producer.start()

            # Créez une instance d'AnalysisDTO avec les données du message
            #analysis_instance = AnalysisDTO(id_client=message['idClient'], product=message['product'])

            # Envoyez l'instance d'AnalysisDTO sérialisée
            #await producer.send_and_wait(topic, value=analysis_instance)


            await producer.send_and_wait(topic, value=message)
            print(f"Message sent to {topic}: {message}")
        except Exception as e:
            print(e)
        finally:
            print("Stop producer")
            await producer.stop()