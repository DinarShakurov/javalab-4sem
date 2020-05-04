package ru.shakurov.websocket_chat.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shakurov.websocket_chat.dto.SignUpDto;
import ru.shakurov.websocket_chat.models.User;
import ru.shakurov.websocket_chat.repositories.UserRepository;
import ru.shakurov.websocket_chat.services.SignUpService;

@Service
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void signUp(SignUpDto signUpDto) {
        User user = User.builder()
                .login(signUpDto.getLogin())
                .hashPassword(passwordEncoder.encode(signUpDto.getPassword()))
                .build();
        userRepository.save(user);
    }
}
