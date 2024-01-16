#!/bin/bash
set -e
mvn clean package
docker-compose build payment-service
docker-compose up -d payment-service
