package ru.shakurov.websocket_chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shakurov.websocket_chat.dto.SignUpDto;
import ru.shakurov.websocket_chat.services.SignUpService;

@Controller
@RequestMapping("/signUp")
public class SignUpController {
    @Autowired
    private SignUpService signUpService;

    @GetMapping
    public String getSignUpPage() {
        return "sign_up";
    }

    @PostMapping
    public String signUp(SignUpDto signUpDto) {
        signUpService.signUp(signUpDto);
        return "redirect:/signIn";
    }
}
