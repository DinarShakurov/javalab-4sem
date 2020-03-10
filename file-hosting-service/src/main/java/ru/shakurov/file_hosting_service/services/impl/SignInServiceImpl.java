package ru.shakurov.file_hosting_service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shakurov.file_hosting_service.models.User;
import ru.shakurov.file_hosting_service.models.dto.SignInDto;
import ru.shakurov.file_hosting_service.repositories.UserRepository;
import ru.shakurov.file_hosting_service.services.SignInService;

import java.util.Optional;

@Component
public class SignInServiceImpl implements SignInService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean signIn(SignInDto signInDto) {
        Optional<User> optionalUser = userRepository.findByEmail(signInDto.getEmail());
        return optionalUser
                .map(user -> user
                        .getPassword()
                        .equals(signInDto.getPassword())
                        && user
                        .getState()
                        .equals("CONFIRMED"))
                .orElse(false);
    }
}
