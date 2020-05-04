package ru.shakurov.websocket_chat.services;

import ru.shakurov.websocket_chat.dto.SignUpDto;

public interface SignUpService {
    void signUp (SignUpDto signUpDto);
}
