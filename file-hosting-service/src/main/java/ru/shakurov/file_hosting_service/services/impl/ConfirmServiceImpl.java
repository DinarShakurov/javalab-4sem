package ru.shakurov.file_hosting_service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shakurov.file_hosting_service.models.User;
import ru.shakurov.file_hosting_service.repositories.UserRepository;
import ru.shakurov.file_hosting_service.services.ConfirmService;

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
