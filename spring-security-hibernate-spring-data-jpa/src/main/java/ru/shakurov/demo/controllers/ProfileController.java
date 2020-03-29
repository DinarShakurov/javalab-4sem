package ru.shakurov.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.shakurov.demo.dto.ProfileDto;
import ru.shakurov.demo.security.details.UserDetailsImpl;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String getProfilePage(ModelMap map, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ProfileDto user = ProfileDto.from(userDetails.getUser());
        map.addAttribute("user", user);
        return "profile";
    }
}
