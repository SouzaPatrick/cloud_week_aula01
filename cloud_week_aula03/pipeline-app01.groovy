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
                    dockerImage = docker.build registry + ":develop"
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
    	stage('Deploy') {
		    steps{
                    step([$class: 'AWSCodeDeployPublisher', 
                        applicationName: 'app01-application',
                        awsAccessKey: "AKIAIKXNYHVLMYJSMMCQ",
                        awsSecretKey: "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",
                        credentials: 'awsAccessKey',
                        deploymentGroupAppspec: false,
                        deploymentGroupName: 'service',
                        deploymentMethod: 'deploy',
                        excludes: '',
                        iamRoleArn: '',
                        includes: '**',
                        pollingFreqSec: 15,
                        pollingTimeoutSec: 600,
                        proxyHost: '',
                        proxyPort: 0,
                        region: 'us-east-2',
                        s3bucket: 'cloud-week-pfs', 
                        s3prefix: '', 
                        subdirectory: '',
                        versionFileName: '',
                        waitForCompletion: true])
            }
        }
        stage('Cleaning up') {
            steps {
                sh "docker rmi $registry:develop"
            }
        }
    }
}