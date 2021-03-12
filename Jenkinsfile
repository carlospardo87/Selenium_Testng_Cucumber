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
                           <img src="https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.redeszone.net%2F2018%2F02%2F19%2Finfectan-los-servidores-jenkins-malware-mina-monero%2F&psig=AOvVaw22hQsivEMBGVxv6vjs31z0&ust=1615665421282000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCMC8hO7Eq-8CFQAAAAAdAAAAABAM">
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