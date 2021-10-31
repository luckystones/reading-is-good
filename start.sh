#!/bin/bash
docker-compose down
docker rmi $(docker images | grep "reading-is-good_api" | awk '{print $3}' ) -f
#docker rmi $(docker images | grep "mongo" | awk '{print $3}' ) -f
mvn clean install
docker-compose up