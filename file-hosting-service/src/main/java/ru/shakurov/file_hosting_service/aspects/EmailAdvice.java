package ru.shakurov.file_hosting_service.aspects;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shakurov.file_hosting_service.services.MailSender;

import java.lang.reflect.Method;

@Component
public class EmailAdvice implements AfterReturningAdvice {
    @Autowired
    private MailSender mailSender;

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        if (method.getName().equals("upload")) {
            mailSender.send();
        }
    }
}
