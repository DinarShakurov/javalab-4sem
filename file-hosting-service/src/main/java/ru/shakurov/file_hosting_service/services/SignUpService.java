package ru.shakurov.file_hosting_service.services;


import ru.shakurov.file_hosting_service.models.dto.SignUpDto;

import java.util.Map;

public interface SignUpService {
    Map<String, Object> signUp(SignUpDto dto);
}
