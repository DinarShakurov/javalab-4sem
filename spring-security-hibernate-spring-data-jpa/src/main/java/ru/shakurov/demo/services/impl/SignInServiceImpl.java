package ru.shakurov.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shakurov.demo.forms.SignInForm;
import ru.shakurov.demo.models.User;
import ru.shakurov.demo.repositories.UserRepository;
import ru.shakurov.demo.services.SignInService;

import java.util.Optional;

@Service
public class SignInServiceImpl implements SignInService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User signIn(SignInForm signInForm) {
        Optional<User> userOptional = userRepository.findByEmail(signInForm.getEmail());
        return null;
    }
}
