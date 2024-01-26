package util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtility {
    public static void sendEmail(String host, String port, final String userName, final char[] password, String toAddress,
                                 String subject, String message) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // creates a new session with an authenticator
        Session session = Session.getInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, new String(password));
            }
        });

        try {
            // create a MimeMessage object
            Message mimeMessage = new MimeMessage(session);

            // set the sender address
            mimeMessage.setFrom(new InternetAddress(userName));

            // set the recipient address
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));

            // set email subject and message
            mimeMessage.setSubject(subject);
            String htmlText = message;
            mimeMessage.setContent(htmlText, "text/html");

            // send the email
            Transport.send(mimeMessage);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
            String hostname = "smtp.gmail.com";
            int port = 587; // Use the appropriate port for your SMTP server
            String username = "";
            char[] password = "yygb zruf iamu vmtg".toCharArray();
            String toAddress = "";
            String subject = "";
            String message = "";

            try {
                sendEmail(hostname, String.valueOf(port), username, password, toAddress, subject, message);
            } catch (Exception e) {
                System.out.println("Failed to send email: " + e.getMessage());
                e.printStackTrace();
            }
    }

}
