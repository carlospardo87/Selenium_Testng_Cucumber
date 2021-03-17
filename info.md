
#Maven 
mvn test -Dsuite=testng -Ddataproviderthreadcount=5

#Run Jenkins 
jenkins-lts

#Conf Report Helper Local
 - Manage your google account -> Security
 - Section Sing in to Google -> disable the options to add more verifications steps
 - Unsafe application access -> enable Allow access of insecure applications
 - Go to Configuration -> section Access IMAP -> enable IMAP

#Conf Report Helper Jenkins
- Go to Manage Jenkins -> Configure Systems 
  (Per default the last LTS Jenkins version in the installation process, you must install all plugins that appears per default between them
should be there the plugin to send jenkins email ).
  
 SECTION JENKINS LOCATION 
- Jenkins URL  (localhost per default)
- Go to Manage Jenkins -> Configure System
- Go to Jenkins Location -> Jenkins URL (Set Jenkins URL)
- Go to System Admin e-mail address  (Should have this format: Jenkins Daemon <foo@acme.org>)
-
SECTION E-MAIL NOTIFICATION
- SMTP server =  smtp.gmail.com
- Default user e-mail suffix = @gmail
- Use SMTP Authentication -> Enable
- User Name = user@domain.xx
- Password = pass
- Use TLS = Enable
- SMTP Port = 578
- Reply-To Address = no-reply@jenkins.foo
- Charset = UTF-8

GENERATE A FAKE EMAIL TO TEST 
 - https://tempmail.ninja/
