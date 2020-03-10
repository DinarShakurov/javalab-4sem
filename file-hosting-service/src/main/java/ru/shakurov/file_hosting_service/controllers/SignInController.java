package ru.shakurov.file_hosting_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.shakurov.file_hosting_service.models.dto.SignInDto;
import ru.shakurov.file_hosting_service.services.SignInService;

import javax.servlet.http.HttpSession;

@Controller
public class SignInController {
    @Autowired
    private SignInService signInService;

    @GetMapping(value = "/signIn")
    public ModelAndView openPage(){
        return new ModelAndView("sign_in");
    }

    @PostMapping(value = "/signIn")
    public ModelAndView signIn(HttpSession httpSession, SignInDto signInDto) {
        if (!signInService.signIn(signInDto))
            return new ModelAndView("redirect:/signIn");
        ModelAndView modelAndView = new ModelAndView("redirect:/files");
        httpSession.setAttribute("user.email", signInDto.getEmail());
        return modelAndView;
    }
}
