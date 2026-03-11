pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                    sh 'mvn clean'
            }
        }
        stage('Test') {
            steps {
                    sh 'mvn test -Dsurefire.suiteXmlFiles=testng.xml'
            }
        }
    }
}
