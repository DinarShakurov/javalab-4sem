package ru.shakurov.websocket_chat.services;

import ru.shakurov.websocket_chat.forms.SignInForm;

import javax.servlet.http.Cookie;

public interface SignInService {
    boolean signIn (SignInForm form);
}
