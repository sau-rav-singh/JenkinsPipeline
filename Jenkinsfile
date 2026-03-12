pipeline {
  agent any

  options {
    ansiColor('xterm')
  }

  stages {

    stage('Build') {
      steps {
        sh 'mvn -Dstyle.color=always clean install'
      }
    }

    stage('Test') {
      steps {
        sh 'mvn -Dstyle.color=always test'
      }
    }
  }
}