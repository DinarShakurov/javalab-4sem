package ru.shakurov.demo.services;

import ru.shakurov.demo.dto.UsersDto;

import java.util.List;

public interface UsersService {
    List<UsersDto> getUsers();
}
