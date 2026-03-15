#!/user/bin/env groovy
//@Library('JenkinsSharedLibrary')_
library identifier: 'JenkinsSharedLibrary@master', retriever: modernSCM(
        [$class: 'GitSCMSource',
         remote: 'https://github.com/sau-rav-singh/JenkinsSharedLibrary.git',
         credentialsId: 'GitHub'])

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
    booleanParam(name: 'DEPLOY', defaultValue: false, description: 'Run Deploy Stage')
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
    stage("Build2") {
      steps {
        script {
          build()
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
    stage("deploy") {
      when {
        branch 'main'
        beforeInput true
        expression {params.DEPLOY}
      }
      input {
        message "Select the environment to deploy to"
        ok "Done"
        parameters {
          choice(name: 'ENV', choices: ['dev', 'staging', 'prod'], description: '')
        }
      }
      steps {
        script {
        env.ENV_ONE = input message: "Select the environment to deploy to", ok: "Done", parameters: [choice(name: 'ONE', choices: ['dev', 'staging', 'prod'], description: '')]
          echo "Deploying to ${ENV}"
          echo "Deploying to ${ENV_ONE}"
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