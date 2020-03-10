package ru.shakurov.file_hosting_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import ru.shakurov.file_hosting_service.services.ConfirmService;

@Controller
public class ConfirmController {
    @Autowired
    private ConfirmService confirmService;

    @GetMapping(value = "/confirm/{confirm-link}")
    public ModelAndView confirm(@PathVariable("confirm-link") String link) {
        ModelAndView modelAndView = new ModelAndView("confirmation");
        if (confirmService.confirm(link))
            modelAndView.addObject("status", "Success");
        else
            modelAndView.addObject("status", "Not success");
        return modelAndView;
    }
}