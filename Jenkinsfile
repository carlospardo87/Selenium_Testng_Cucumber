pipeline {
    agent any
        options { timeout(time: 5) } // Stop execution if spend more than 5 min

     stages {
               /*  stage("Clean") {
                    steps {
                        //git url: 'https://github.com/carlospardo87/TestngCucumberBoilerPlate.git'
                        // Run Maven on a Unix agent.
                     sh "mvn -Dmaven.test.failure.ignore=true clean package"
                    }
                } */
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
               sh 'mvn test -Dsuite=testng'
            }
        }
    }
    /* post {
        always {
            mail to: 'equipo@ricardogeek.com',
            subject: "Se completo el pipeline: ${currentBuild.fullDisplayName}",
            body: "Se ha terminado la compilación, porfavor revisa: ${env.BUILD_URL}"
        }
    } */


}