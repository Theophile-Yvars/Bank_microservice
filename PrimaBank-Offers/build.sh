#!/bin/bash

IMAGE_NAME=primabank/analysis
IMAGE_TAG_CURRENT=0.0.0

mvn clean package # -DskipTests

echo "### Build new image ${IMAGE_NAME}:${IMAGE_TAG_CURRENT}"
docker build --no-cache --tag ${IMAGE_NAME}:${IMAGE_TAG_CURRENT} .