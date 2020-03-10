package ru.shakurov.file_hosting_service.services;

import ru.shakurov.file_hosting_service.models.dto.SignInDto;

public interface SignInService {
    boolean signIn(SignInDto signInDto);
}
