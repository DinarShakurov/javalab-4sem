package ru.shakurov.websocket_chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shakurov.websocket_chat.forms.SignInForm;
import ru.shakurov.websocket_chat.services.SecurityService;
import ru.shakurov.websocket_chat.services.SignInService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Controller
@RequestMapping("/signIn")
public class SignInController {
    @Autowired
    private SignInService signInService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private Environment environment;

    @GetMapping
    public String getSignInPage() {
        return "sign_in";
    }

    @PostMapping
    public String signIn(HttpServletResponse httpServletResponse, SignInForm signInForm) {
        try {
            if (signInService.signIn(signInForm)) {
                String token = securityService.createToken(signInForm);

                Cookie cookie = new Cookie(Objects.requireNonNull(environment.getProperty("security.token.name")), token);
                cookie.setMaxAge(Integer.parseInt(Objects.requireNonNull(environment.getProperty("security.token.lifetime"))));

                httpServletResponse.addCookie(cookie);
                return "redirect:/rooms";
            }
            return "redirect:/signIn?error1";
        } catch (Exception e) {
            return "redirect:/signIn?error2";
        }
    }
}
