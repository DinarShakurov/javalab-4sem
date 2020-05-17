package ru.shakurov.jlmq.jlmq_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shakurov.jlmq.jlmq_server.entity.Consumer;
import ru.shakurov.jlmq.jlmq_server.entity.ConsumerState;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
    List<Consumer> findByState(ConsumerState state);
    Optional<Consumer> findByUsername(String username);
}
