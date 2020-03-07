package ru.shakurov.servlet_spring.services;

import ru.shakurov.servlet_spring.models.dto.SignUpDto;

public interface SignUpService {
    void signUp(SignUpDto dto);
}
