package ru.shakurov.servlet_spring.services;

import ru.shakurov.servlet_spring.EmailInfo;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailSender {
    private String text;
    private String to;

    public MailSender(String text, String to) {
        this.text = text;
        this.to = to;
    }

    public void send() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailInfo.EMAIL, EmailInfo.PASSWORD);
            }
        });

        MimeMessage mimeMessage = prepareMessage(session);
        try {
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException(e);
        }
    }

    private MimeMessage prepareMessage(Session session) {
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(EmailInfo.EMAIL));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setContent(text, "text/html;charset=UTF-8");
            mimeMessage.setSubject("Confirmation");
            return mimeMessage;
        } catch (MessagingException e) {
            throw new IllegalStateException(e);
        }
    }
}
