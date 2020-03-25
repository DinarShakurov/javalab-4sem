package ru.shakurov.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shakurov.demo.forms.SignUpForm;
import ru.shakurov.demo.models.Role;
import ru.shakurov.demo.models.State;
import ru.shakurov.demo.models.User;
import ru.shakurov.demo.repositories.UserRepository;
import ru.shakurov.demo.services.SignUpService;

@Service
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpForm signUpForm) {
        String hashPassword = passwordEncoder.encode(signUpForm.getPassword());

        User user = User.builder()
                .email(signUpForm.getEmail())
                .hashPassword(hashPassword)
                .name(signUpForm.getName())
                .role(Role.USER)
                .state(State.ACTIVE)
                .build();

        userRepository.save(user);
    }
}
