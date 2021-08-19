#!/bin/bash
sudo apt-get update -y
sudo apt-get install -y apt-transport-https ca-certificates curl gnupg-agent software-properties-common
sudo apt-get install -y docker.io
sudo chmod 777 /var/run/docker.sock