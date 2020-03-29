package ru.shakurov.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shakurov.demo.dto.UsersDto;
import ru.shakurov.demo.models.User;
import ru.shakurov.demo.repositories.UserRepository;
import ru.shakurov.demo.services.UsersService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UsersDto> getUsers() {
        List<User> list =  userRepository.findAll();
        return list.stream()
                .map(UsersDto::from)
                .collect(Collectors.toList());
    }
}
