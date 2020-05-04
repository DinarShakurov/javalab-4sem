package ru.shakurov.websocket_chat.services;

import ru.shakurov.websocket_chat.dto.UserDto;
import ru.shakurov.websocket_chat.forms.SignInForm;

public interface SecurityService {
    String createToken(SignInForm signInForm);

    UserDto authorizeByToken(String token);
}
