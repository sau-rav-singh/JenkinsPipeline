pipeline {
  agent any

  options {
    ansiColor('xterm')
  }

  stages {

    stage('Build') {
      steps {
        sh 'mvn clean install'
      }
    }

    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }
  }
}