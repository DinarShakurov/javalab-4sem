package ru.shakurov.file_hosting_service.services.impl;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shakurov.file_hosting_service.models.User;
import ru.shakurov.file_hosting_service.models.dto.SignUpDto;
import ru.shakurov.file_hosting_service.repositories.UserRepository;
import ru.shakurov.file_hosting_service.services.MailSender;
import ru.shakurov.file_hosting_service.services.SignUpService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component ("default_SignUpService")
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UserRepository userRepository;
/*    @Autowired
    private Configuration cfg;*/

    @Override
    public Map<String, Object> signUp(SignUpDto dto) {
        String confirmLink = UUID.randomUUID().toString();
        String link = "http://localhost:8080/confirm/" + confirmLink;

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .link(confirmLink)
                .state("NOT_CONFIRMED")
                .build();
        userRepository.save(user);

        Map<String, Object> map = new HashMap<>();
        map.put("link", link);
        map.put("emailTo", user.getEmail());
        map.put("templateName", "email_confirmation.ftlh");
        return map;
        /*new MailSender().setConfiguration(cfg)
                .setLink(link)
                .setTemplateName("email_confirmation.ftlh")
                .setTo(user.getEmail())
                .send();*/
    }
}
