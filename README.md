##Maven command 
    mvn test -Dsuite=testng -Ddataproviderthreadcount=5

## Change Jenkins PORT (Brew Installation)
    cd /usr/local/Cellar/jenkins-lts/${current_version}
    nano homebrew.mxcl.jenkins-lts.plist
    change --httpPort={desired_port}
    brew services stop jenkins-lts
    brew services start jenkins-lts

##Run Jenkins agent
    jenkins-lts

## Conf Report Helper Local
- Manage your google account -> Security
- Section Sing in to Google -> disable the options to add more verifications steps
- Unsafe application access -> enable Allow access of insecure applications
- Go to Configuration -> section Access IMAP -> enable IMAP

## Conf Report Helper Jenkins
- Go to Manage Jenkins -> Configure Systems
  (Per default the last LTS Jenkins version in the installation process, you must install all plugins that appears per default between them
  should be there the plugin to send jenkins email ).

#### SECTION JENKINS LOCATION
- Jenkins URL  (localhost per default)
- Go to Manage Jenkins -> Configure System
- Go to Jenkins Location -> Jenkins URL (Set Jenkins URL)
- Go to System Admin e-mail address  (Should have this format: Jenkins Daemon <foo@acme.org>)
-
#### SECTION E-MAIL NOTIFICATION
- SMTP server =  smtp.gmail.com
- Default user e-mail suffix = @gmail
- Use SMTP Authentication -> Enable
- User Name = user@domain.xx
- Password = pass
- Use TLS = Enable
- SMTP Port = 578
- Reply-To Address = no-reply@jenkins.foo
- Charset = UTF-8

## GENERATE A FAKE EMAIL TO TEST
    https://tempmail.ninja/


#Selenoid Installation

###Get Docker MacOS installer:
    https://hub.docker.com/editions/community/docker-ce-desktop-mac/

### Download and install it on MacOS
    curl -s https://aerokube.com/cm/bash | bash && ./cm selenoid start — vnc

### Download browser images
    docker pull selenoid/{browserName:browserVersion}

###Run Selenoid
    ./cm selenoid start — vnc

### Run Selenoid GUI
    ./cm selenoid-ui start

### Start and stop docker container
./cm selenoid-ui stop

./cm selenoid-ui start --port 8081   (Changing default port 8080 by 8081)

### Access Selenoid GUI
    http://localhost:8080/

### Connection with Selenium
    http://127.0.0.1:4444/wd/hub
