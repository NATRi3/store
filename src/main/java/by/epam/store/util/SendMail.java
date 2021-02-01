package by.epam.store.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
    public static final Logger logger = LogManager.getLogger(SendMail.class);
    private static final String SENDER_EMAIL_ADDRESS = "mailforwebproject3@gmail.com";
    private static final String SENDER_EMAIL_PASSWORD = "123789qEW";
    private static final String SENDER_HOST = "smtp.gmail.com";
    private static final String SENDER_PORT = "587";
    private static final String ACTIVATION_URL = "http://localhost:8080/store_war_exploded/controller?command=activation&activation_code=";

    public void sendActivationMailTo(String mail, long id) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SENDER_HOST);
        properties.put("mail.smtp.port", SENDER_PORT);
        properties.put("mail.from", SENDER_EMAIL_ADDRESS);
        properties.put("mail.smtp.password", SENDER_EMAIL_PASSWORD);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL_ADDRESS, SENDER_EMAIL_PASSWORD);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL_ADDRESS));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(mail));
            message.setSubject("Service");
            message.setText(ACTIVATION_URL+id);//TODO
            Transport.send(message);
            logger.info("Message successfully send");
        } catch (MessagingException e) {
            logger.error("Failed activation mail send!");
        }
    }
}
