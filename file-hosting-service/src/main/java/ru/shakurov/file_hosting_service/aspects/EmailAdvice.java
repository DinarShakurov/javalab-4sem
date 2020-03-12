package ru.shakurov.file_hosting_service.aspects;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shakurov.file_hosting_service.models.UploadFile;
import ru.shakurov.file_hosting_service.services.MailSender;

import java.lang.reflect.Method;
import java.util.Map;

@Component
public class EmailAdvice implements AfterReturningAdvice {
    @Autowired
    private MailSender mailSender;

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        if (method.getName().equals("upload")) {
            Map<String, Object> map = (Map<String, Object>) returnValue;

            UploadFile uploadFile = (UploadFile) map.get("uploadFile");
            String emailTo = (String) map.get("emailTo");
            String templateName = (String) map.get("templateName");
            String link = "http://localhost:8080/files/" + uploadFile.getNameOnDisk();

            mailSender
                    .setLink(link)
                    .setSubject("Downloader link")
                    .setTemplateName(templateName)
                    .setTo(emailTo)
                    .send();
        }
        if (method.getName().equals("signUp")) {
            Map<String, Object> map = (Map<String, Object>) returnValue;

            String emailTo = (String) map.get("emailTo");
            String templateName = (String) map.get("templateName");
            String link = (String) map.get("link");

            mailSender
                    .setSubject("Registration confirmation")
                    .setLink(link)
                    .setTemplateName(templateName)
                    .setTo(emailTo)
                    .send();
        }
    }
}
