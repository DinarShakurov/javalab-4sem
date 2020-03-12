package ru.shakurov.file_hosting_service.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.shakurov.file_hosting_service.models.dto.SignUpDto;
import ru.shakurov.file_hosting_service.services.SignUpService;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import java.io.File;
import java.net.URLDecoder;

@Controller
public class SignUpController {
    @Qualifier("proxied_SignUpService")
    @Autowired
    private SignUpService signUpService;

    @GetMapping(value = "/signUp")
    public ModelAndView openPage() {
        return new ModelAndView("sign_up");
    }

    @PostMapping(value = "/signUp")
    public ModelAndView signUp(SignUpDto signUpDto) {
        signUpService.signUp(signUpDto);
        return new ModelAndView("redirect:/signUp");
    }

    private ModelAndView getSignUpView() {
        return new ModelAndView("sign_up");
    }
}
