package ru.shakurov.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SignInController {
    @GetMapping("/signIn")
    public String getSignInPage(Authentication authentication, ModelMap map, HttpServletRequest httpServletRequest) {
        if (authentication != null){
            return "redirect:/profile";
        }
        if (httpServletRequest.getParameterMap().containsKey("error")) {
            map.addAttribute("error", "true");
        }
        map.addAttribute("username", "Кожаный мешок");
        return "signIn";
    }
}
