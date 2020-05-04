package ru.shakurov.websocket_chat.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shakurov.websocket_chat.forms.SignInForm;
import ru.shakurov.websocket_chat.models.User;
import ru.shakurov.websocket_chat.repositories.UserRepository;
import ru.shakurov.websocket_chat.services.SignInService;

@Service
public class SignInServiceImpl implements SignInService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean signIn(SignInForm form) {
        User user = userRepository.findUserByLogin(form.getLogin()).orElseThrow(IllegalArgumentException::new);
        return passwordEncoder.matches(form.getPassword(), user.getHashPassword());
    }
}
