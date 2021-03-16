package helpers;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static runners.CucumberRunner.config;

public class EmailHelper {

    /**
     * Note
     * Firstly we need to configure gmail  Manage your google account -> Security
     * - Section Sing in to Google - disable the options to add more verifications steps
     * - Unsafe application access - enable Allow access of insecure applications
     *
     * Secondly  we need go to Configuration -> section Access IMAP -> enable IMAP
     *
     * Jenkins config
     * Go to Manage Jenkins -> Configure Systems (Per default the last LTS Jenkins version in the install process, you must install all of plugins that appears per default between them
     * should be there the plugin to send jenkins email ) :
     * ---- SECTION JENKINS LOCATION ---
     * 1- Jenkins URL  (localhost per default)
     * 2- System Admin e-mail address  (Should have this format: Jenkins Daemon <foo@acme.org>)
     *
     *---- SECTION  E-mail Notification ------
     * 1 - SMTP server =  smtp.gmail.com
     * 2- Default user e-mail suffix = @gmail
     * 3- Use SMTP Authentication -> Enable
     * 4- User Name = user@domain.xx
     * 5- Password = pass
     * 6- Use TLS = Enable
     * 7- Reply-To Address = no-reply@jenkins.foo
     * 8- Charset = UTF-8
     *
     *
     * **** GENERATE A FAKE EMAIL TO TEST ****
     * https://tempmail.ninja/
     */

    public static void sendEmailReport() {

        final String USERNAME = config.getProperty("userEmail");
        final String PASSWORD = config.getProperty("passEmail");
        final String HOST = "smtp.gmail.com";
        final String PORT = "587";  // Other port available 25, 2525 , 465

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("kingeneric21@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("bmaggioi_l667d@fuluj.com"));
            message.setSubject("Testing Regression");
            message.setText("Regression status");

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            //Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            String file = "path of file to be attached";
            String fileName = "target//cucumber-html-reports//overview-features.html";
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            //multipart.addBodyPart(messageBodyPart);

            //message.setContent(multipart);

            System.out.println("\n-----------  SENDING EMAIL ... -----------------------------------");
            Transport.send(message);
            System.out.println("-----------  DONE -----------------------------------");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
