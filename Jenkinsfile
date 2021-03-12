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
            echo 'Clean maven dependencies'
               sh 'mvn clean -DskipTests'
            }
        }

        stage('Test') {
             steps {
             echo 'Running test suite'
                 sh 'mvn test -Dsuite=testng'
            }
        }
    }

    /* post {
            always {
               cucumber '**//* cucumber.json'

                echo 'Sending email'
                 mail to: 'bmaggioi_l667d@fuluj.com',
                          subject: "Status of pipeline: ${currentBuild.fullDisplayName}",
                          body: "${env.BUILD_URL} has result ${currentBuild.result}",
                          sortingMethod: 'ALPHABETICAL'


                          mail to: 'bmaggioi_l667d@fuluj.com',
                              subject: "Job '${JOB_NAME}' (${BUILD_NUMBER}) is waiting for input",
                              body: "Please go to ${BUILD_URL} and verify the build"
            }
        } */


        post {
                     always {
                         echo 'This will always run'
                         cucumber '**/cucumber.json'


                          emailext(
                          attachmentsPattern: "report-output/WebReport/ExtentWeb.html",
                          to: 'bmaggioi_l667d@fuluj.com',
                          from: '',
                          mimeType: 'text/html',
                          replyTo: '',
                          subject: "Status of pipeline: ${currentBuild.result} - Project name -> ${env.JOB_NAME}",
                          //body: "<b>Example</b> <br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> URL de build: ",
                          body: "${env.BUILD_URL}cucumber-html-reports/overview-features.html"
                          //body: "<b>Example</b> <br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> Report Link: <a href=${env.BUILD_URL}cucumber-html-reports/overview-features.html</a>"
                          )
                     }

        }

}