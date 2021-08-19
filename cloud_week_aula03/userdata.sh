#!/bin/bash

# Install Dependencies
sudo yum update
sudo yum install -y aws-cli ruby wget

# Install Docker
sudo amazon-linux-extras install docker-compose
sudo service docker start
sudo usermod -a -G docker ec2-user

# Install CodeDeploy Agent
wget https://aws-codedeploy-us-east-1.s3.us-east-1.amazonaws.com/latest/install
chmod +x ./install
sudo ./install auto
sudo service codedeploy-agent start
sudo chmod 777 /etc/init.d/codedeploy-agent