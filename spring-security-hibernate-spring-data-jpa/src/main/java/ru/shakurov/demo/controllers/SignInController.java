package ru.shakurov.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SignInController {
    @GetMapping("/signIn")
    public String getSignInPage(ModelMap map, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getParameterMap().containsKey("error")) {
            map.addAttribute("error", "true");
        }
        return "signIn";
    }
}
