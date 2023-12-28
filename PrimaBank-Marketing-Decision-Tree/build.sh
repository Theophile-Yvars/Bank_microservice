#!/bin/bash

IMAGE_NAME=primabank/marketingdecisiontree
IMAGE_TAG_CURRENT=0.0.0

echo "### Build new image ${IMAGE_NAME}:${IMAGE_TAG_CURRENT}"
#docker build --no-cache --tag ${IMAGE_NAME}:${IMAGE_TAG_CURRENT} .
docker build --tag ${IMAGE_NAME}:${IMAGE_TAG_CURRENT} .
