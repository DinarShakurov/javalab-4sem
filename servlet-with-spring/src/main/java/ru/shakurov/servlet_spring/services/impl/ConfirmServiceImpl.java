package ru.shakurov.servlet_spring.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shakurov.servlet_spring.models.User;
import ru.shakurov.servlet_spring.repositories.UserRepository;
import ru.shakurov.servlet_spring.services.ConfirmService;

import java.util.Optional;

@Component
public class ConfirmServiceImpl implements ConfirmService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean confirm(String link) {
        Optional<User> userOptional = userRepository.findByLink(link);
        if (userOptional.isPresent()) {
            userRepository.updateState("CONFIRMED", link);
            return true;
        }
        return false;
    }
}
