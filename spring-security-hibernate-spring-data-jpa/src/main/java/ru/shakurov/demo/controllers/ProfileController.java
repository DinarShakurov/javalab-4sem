package ru.shakurov.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {


    @GetMapping
    public String getProfilePage(Model map) {
        return "profile";
    }
}
