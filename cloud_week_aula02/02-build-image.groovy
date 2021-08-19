pipeline {
    agent any

    environment { 
        registry = "felipe6659/devops_cloud_week" 
        registryCredential = 'dockerhub_id' 
        dockerImage = '' 
    }

    stages {
        stage('Clone Repository') {
            steps {
                git url: 'https://gitlab.com/souza.felipe.patrick/app01.git'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
        stage('Send image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry( '', registryCredential ) { 
                    dockerImage.push() 
                    }
                }
            }
        }
        stage('Cleaning up') {
            steps {
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }
    }
}