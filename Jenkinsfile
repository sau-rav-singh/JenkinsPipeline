pipeline {
  agent any

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