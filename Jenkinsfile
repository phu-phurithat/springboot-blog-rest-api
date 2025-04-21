pipeline {
  agent any

  environment {
    DOCKER_REGISTRY = 'phuphurithat'
    IMAGE_NAME = 'blog-api'
    IMAGE_TAG = "${BUILD_NUMBER}"
    // KUBE_CONFIG = credentials('kubeconfig') // Jenkins credential ID
    DB_HOST='localhost'
    DB_NAME='blog'
    DB_PASSWORD=''
    DB_PORT='3306'
    DB_USERNAME='root'
  }

  stages {
    stage('Checkout') {
      steps {
        git 'https://github.com/your-org/post-service.git'
      }
    }

    stage('Docker Login') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
          sh """
            echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
            echo "Docker login successful"
          """
        }
      }
    }

    stage('Test') {
      steps {
        sh """
          mvn test
          echo "Tests completed successfully"
        """
      }
    }

    stage('Build') {
      steps {
        sh """
          mvn clean package -DskipTests
          echo "Build completed successfully"
        """
      }
    }

    stage('Build Docker Image') {
      steps {
        script {
          sh """
          docker build -t $DOCKER_REGISTRY/$IMAGE_NAME:$IMAGE_TAG .
          echo "Docker image built successfully"
          """
          
        }
      }
    }

    // stage('Push to Registry') {
    //   steps {
    //     withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
    //       sh """
    //         echo $PASS | docker login -u $USER --password-stdin
    //         docker push $DOCKER_REGISTRY/$IMAGE_NAME:$IMAGE_TAG
    //       """
    //     }
    //   }
    // }

    // stage('Deploy to Kubernetes') {
    //   steps {
    //     withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG')]) {
    //       sh """
    //         kubectl set image deployment/post-service post-service=$DOCKER_REGISTRY/$IMAGE_NAME:$IMAGE_TAG --kubeconfig=$KUBECONFIG
    //       """
    //     }
    //   }
    // }

    // stage('Notify') {
    //   steps {
    //     echo "Deployment completed for build $IMAGE_TAG"
    //     // หรือ integrate Slack/email notification
    //   }
    // }
  }

  post {
    failure {
      echo "Build or Deploy Failed!"
    }
  }
}
