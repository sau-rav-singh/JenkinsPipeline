def gv
pipeline {
  agent any
  tools {
    maven 'Maven_3.9.13'
  }
  parameters {
    string(name: 'TARGET_ENV', defaultValue: 'staging', description: 'Deploy target')
    booleanParam(name: 'RUN_SMOKE', defaultValue: true, description: 'Run smoke tests?')
    choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Browser')
  }
  environment {
    GIT_TOKEN = '12345'
    DOCKER_SECRET = credentials('dockerhub-credentials')
  }
  stages {
    stage("Init") {
      steps {
        script {
          gv = load "script.groovy"
        }
      }
    }
    stage("Build") {
      steps {
        script {
          gv.build()
        }
      }
    }
    stage('Test') {
      when {
        expression {
          params.RUN_SMOKE == true
        }
      }
      steps {
        withCredentials([usernamePassword(credentialsId: 'dummyCredential', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
          echo "Some Script ${USER} ${PASS}"
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