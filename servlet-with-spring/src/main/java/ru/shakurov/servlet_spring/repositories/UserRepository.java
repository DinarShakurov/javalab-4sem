package ru.shakurov.servlet_spring.repositories;

import ru.shakurov.servlet_spring.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Integer, User> {
    Optional<User> findByLink(String link);

    void updateState(String state, String userLink);
}
