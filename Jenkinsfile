pipeline {
    agent any
    stages {

    // tools - will be installed automatically on thw env where Jenkins run
    // These tools need to be configured in Manage Jenkins -> Global Tool Configuration
    /* tools {
            maven 'Maven 3.3.9'
            jdk 'jdk8'
        } */
     stage ('Initialize') {
                steps {
                    sh '''
                        echo "PATH = ${PATH}"
                        echo "M2_HOME = ${M2_HOME}"
                    '''
                }
            }
        stage('Build') {
            steps {
               sh 'mvn test -Dsuite=testng'
            }
        }
    }
}