package ru.shakurov.file_hosting_service.services;


import ru.shakurov.file_hosting_service.models.dto.SignUpDto;

public interface SignUpService {
    void signUp(SignUpDto dto);
}
