pipeline {
    agent any
        options { timeout(time: 5) } // Stop execution if spend more than 5 min

     stages {
     stage ('Initialize') {
                steps {
                  echo 'Getting PATH'
                    sh '''
                        echo "PATH = ${PATH}"
                        echo "M2_HOME = ${M2_HOME}"
                    '''
                }
            }
        stage('Build') {
            steps {
            echo 'Running test suite'
               sh 'mvn clean test -Dsuite=testng'
            }
        }
    }

    post {
            always {
                cucumber '**/cucumber.json'

                mail to: 'bmaggioi_l667d@fuluj.com',
                          subject: "Status of pipeline: ${currentBuild.fullDisplayName}",
                          body: "${env.BUILD_URL} has result ${currentBuild.result}"
            }
        }
}