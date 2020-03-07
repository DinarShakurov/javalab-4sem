package ru.shakurov.servlet_spring.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<ID, T> {
    Optional<T> find(ID id);

    List<T> findAll();

    void save(T entity);

    void delete(ID id);
}
