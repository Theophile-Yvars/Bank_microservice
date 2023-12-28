#!/bin/bash

if [ "$1" == "-k" ]; then
  # Kafka
  curl -X GET http://localhost:8080/transaction/choose/kafka
elif [ "$1" == "-r" ]; then
  # Rabbit
  curl -X GET http://localhost:8080/transaction/choose/rabbit
else
  echo "Usage: $0 [-k|-r]"
  exit 1
fi
