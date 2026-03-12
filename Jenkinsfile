pipeline {
  agent any

  options {
    ansiColor('xterm')
  }

  stages {

    stage('Build') {
      steps {
        sh 'mvn -B clean install'
      }
    }

    stage('Test') {
      steps {
        sh 'mvn -B test'
      }
    }
  }
}