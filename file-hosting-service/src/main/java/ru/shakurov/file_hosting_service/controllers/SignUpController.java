package ru.shakurov.file_hosting_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.shakurov.file_hosting_service.models.dto.SignUpDto;
import ru.shakurov.file_hosting_service.services.SignUpService;

@Controller
public class SignUpController {
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
