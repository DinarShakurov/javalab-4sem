package ru.shakurov.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.shakurov.demo.forms.SignUpForm;
import ru.shakurov.demo.services.SignUpService;

@Controller
public class SignUpController {

    @Autowired
    private SignUpService service;

    @GetMapping("/signUp")
    public String getSignUpPage() {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(SignUpForm signUpForm) {
        service.signUp(signUpForm);
        return "redirect:/signIn";
    }
}
