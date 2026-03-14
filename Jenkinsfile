pipeline {
  agent any
  tools {
  maven 'Maven_3.9.13' // name must match what's configured in Global Tool Config
  }
  parameters {
      string(name: 'TARGET_ENV', defaultValue: 'staging', description: 'Deploy target')
      booleanParam(name: 'RUN_SMOKE', defaultValue: true, description: 'Run smoke tests?')
      choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Browser')
    }
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
          GIT_BRANCH == 'main' || GIT_BRANCH == 'master'
        }
      }
      steps {
      withCredentials([usernamePassword(credentials: 'dummyCredential', userNameVariable: USER, passwordVariable: PASS)]) {
            sh "Some Script ${USER} ${PASS}"
            sh 'mvn -B test'
            echo "Test Running in ${params.TARGET_ENV}"
          }
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