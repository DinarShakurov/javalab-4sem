package ru.shakurov.jlmq.jlmq_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shakurov.jlmq.jlmq_server.entity.Queue;

import java.util.Optional;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {

    Optional<Queue> findByName(String name);

    Optional<Queue> getByName(String name);

}
