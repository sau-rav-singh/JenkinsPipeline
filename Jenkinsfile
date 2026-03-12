pipeline {
  agent any

  environment{
    GIT_TOKEN = '12345'
    DOCKER_SECRET= credentials('dockerhub-credentials')
  }
  stages {

    stage('Build') {
      steps {
        sh 'mvn -B clean install'
        echo "Git Tokens is ${GIT_TOKEN}"
        echo "Docker Secret is ${DOCKER_SECRET}"
      }
    }

    stage('Test') {
      when {
        expression {
          BRANCH_NAME == 'main' || BRANCH_NAME == 'master'
        }
      }
      steps {
        sh 'mvn -B test'
      }
    }
  }

  post {

    always {
      junit '**/target/surefire-reports/*.xml'
    }

    success {
      archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
    }

    failure {
      echo 'Build failed. Check the logs for more information.'
    }
  }

}