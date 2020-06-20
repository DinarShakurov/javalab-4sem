package ru.itis.springbootdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.springbootdemo.models.Document;

public interface DocumentsRepository extends JpaRepository<Document, Long> {
}
