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
                           <h2><a href="${env.JOB_DISPLAY_URL}">Pipeline ${currentBuild.projectName}</a></h2>


                           <table>
                               <colgroup>
                                   <col style=”background-color: gray”>
                                   <col span=”2” style=”background-color:yellow;”>
                               </colgroup>    <thead>
                                   <tr style=”background-color: black; color:white”>
                                       <th>País</th>
                                       <th>Capital</th>
                                       <th>Otras ciudades</th>
                                   </tr>
                                </thead>
                               <tbody>
                                   <tr>
                                       <th>España</th>
                                       <td>Madrid</td>
                                       <td>Barcelona, Zaragoza, Sevilla, Valencia,
                                           Bilbao</td>
                                   </tr>
                                   <tr>
                                       <th>Francia</th>
                                       <td>París</td>
                                       <td>Lyon, Marsella, Nantes, Toulouse</td>
                                   </tr>
                                   <tr>
                                       <th>Portugal</th>
                                       <td>Lisboa</td>
                                       <td>Oporto, Coimbra</td>
                                   </tr>
                               </tbody>
                           </table>


                           </body>
                           </html>
                           """,
                           to: 'bmaggioi_l667d@fuluj.com'
                          )
                     }

        }

}