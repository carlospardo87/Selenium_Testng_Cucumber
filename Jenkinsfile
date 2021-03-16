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
                           subject: "STATUS OF PIPELINE: ${currentBuild.result}",
                           mimeType: 'text/html',
                           body: """
                           <html>
                           <body>
                           <center><img src="https://www.redeszone.net/app/uploads-redeszone.net/2018/02/servidores-de-jenkins-infectados-con-malware-que-mina-monero-930x452.png" width="300" height="200"></center>
                           <h1>Pipeline: ${currentBuild.fullDisplayName}</h1>
                           <h2><a href="${env.BUILD_URL}cucumber-html-reports/overview-features.html">Build ${currentBuild.id} - Results Link </a></h2>
                           <h2><a href="${env.BUILD_URL}">Build ${currentBuild.id} Results </a></h2>
                           <h2><a href="${env.JOB_DISPLAY_URL}">Pipeline ${currentBuild.projectName}</a></h2>
                           </body>
                           </html>
                           """,
                           to: 'bmaggioi_l667d@fuluj.com'
                          )
                     }

        }

}