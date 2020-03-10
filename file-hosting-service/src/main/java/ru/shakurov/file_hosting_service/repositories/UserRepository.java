package ru.shakurov.file_hosting_service.repositories;

import ru.shakurov.file_hosting_service.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Integer, User> {
    Optional<User> findByLink(String link);

    void updateState(String state, String userLink);

    Optional<User> findByEmail(String email);
}
