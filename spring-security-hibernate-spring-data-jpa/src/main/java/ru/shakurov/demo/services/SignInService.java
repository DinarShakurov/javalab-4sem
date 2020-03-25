package ru.shakurov.demo.services;

import ru.shakurov.demo.forms.SignInForm;
import ru.shakurov.demo.models.User;

public interface SignInService {
    User signIn(SignInForm signInForm);
}
