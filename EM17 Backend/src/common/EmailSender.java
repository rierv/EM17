/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Raffolox
 */
public class EmailSender {
    private final static String username = "eventmanager17ingsw@gmail.com";
    private final static String password = "ThisIsAVeryStrongPassword";
    private static Properties props;
    private static Session session;
    
    static {
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            }
        );
    }
    
    public static void sendEmail(String receiver, String subject, String body) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("eventmanager17ingsw@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiver));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            
            System.out.println("Sent a mail to " + receiver);
        } catch (MessagingException e) {
            System.out.println("Error while sending a mail to " + receiver + ": subject " + subject + " ------- body " + body);
            e.printStackTrace();
        }
    }
}
