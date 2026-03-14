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

    stage('Build') {
      steps {
        sh 'mvn -B clean install'
        echo "Git Token is ${GIT_TOKEN}"
        echo "Docker Secret is ${DOCKER_SECRET}"
      }
    }

    stage('Test') {
      steps {
        echo "Branch is: ${env.GIT_BRANCH}"
        withCredentials([usernamePassword(credentialsId: 'dummyCredential', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
          sh "Some Script ${USER} ${PASS}"
          sh 'mvn -B test'
          echo "Test Running in ${params.TARGET_ENV}"
        }
      }
    }                          // <-- stage('Test') closing brace

  }                            // <-- stages closing brace

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

}                              // <-- pipeline closing brace