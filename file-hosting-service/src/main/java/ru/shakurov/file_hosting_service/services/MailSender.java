package ru.shakurov.file_hosting_service.services;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shakurov.file_hosting_service.EmailInfo;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.DataSource;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class MailSender {
    private String link;
    private String to;
    private String templateName;
    private String subject;

    @Autowired
    private Configuration configuration;

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

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(EmailInfo.EMAIL));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(this.subject);

            MimeMultipart mimeMultipart = new MimeMultipart("related");

            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = prepareMessageContent();
            messageBodyPart.setContent(htmlText, "text/html;charset=UTF-8");
            mimeMultipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();


            String path = this.getClass().getClassLoader().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            DataSource ds = new FileDataSource(fullPath + "../templates/pappich.jpg");

            messageBodyPart.setDataHandler(new DataHandler(ds));
            messageBodyPart.setHeader("Content-ID", "<pappich>");

            mimeMultipart.addBodyPart(messageBodyPart);

            mimeMessage.setContent(mimeMultipart);
            Transport.send(mimeMessage);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    private String prepareMessageContent() {
        try {
            Template template = configuration.getTemplate(templateName);
            Map<String, Object> map = new HashMap<>();
            map.put("link", this.link);
            Writer out = new StringWriter();
            template.process(map, out);
            return out.toString();
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }
    }

    public MailSender setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public MailSender setTemplateName(String templateName) {
        this.templateName = templateName;
        return this;
    }

    public MailSender setLink(String link) {
        this.link = link;
        return this;
    }

    public MailSender setTo(String to) {
        this.to = to;
        return this;
    }

/*    public MailSender setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }*/

}
