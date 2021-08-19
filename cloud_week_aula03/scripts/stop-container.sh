#!/bin/bash
docker rm -f `docker ps -qa` || true
docker rmi -f `docker images -q` || true