pipeline {
  agent any
  tools {
        jdk 'jdk21'
        maven 'maven3'
    }
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

    HOME = "${WORKSPACE}"
  }

  stages {
    stage('Info') {
      steps {
        sh 'java -version && mvn -version'
      }
    }
    stage('Checkout') {
      steps {
        git branch: 'main',
        url: 'https://github.com/phu-phurithat/springboot-blog-rest-api.git'
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

    // stage('Test') {
    //   steps {
    //     sh """
    //       mvn test
    //       echo "Tests completed successfully"
    //     """
    //   }
    // }

    stage('Docker Login') {
      agent {
        docker {
          image 'docker:24.0.7-dind'
          args '-v /var/run/docker.sock:/var/run/docker.sock -v /usr/local/bin/docker:/usr/local/bin/docker'
          reuseNode true
        }
      }
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
          sh """
            echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
            echo "Docker login successful"
          """
        }
      }
    }

    stage('Build Docker Image') {
      steps {
        script {
          sh """
          DOCKER_BUILDKIT=1 docker build -t $DOCKER_REGISTRY/$IMAGE_NAME:$IMAGE_TAG .
          echo "Docker image built successfully"
          """
          
        }
      }
    }

    stage('Push to Registry') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
          sh """
            echo $PASS | docker login -u $USER --password-stdin
            docker push $DOCKER_REGISTRY/$IMAGE_NAME:$IMAGE_TAG
          """
        }
      }
    }

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
