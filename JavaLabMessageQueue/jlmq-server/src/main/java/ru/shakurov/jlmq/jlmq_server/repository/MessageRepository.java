package ru.shakurov.jlmq.jlmq_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.shakurov.jlmq.jlmq_server.entity.Message;
import ru.shakurov.jlmq.jlmq_server.entity.MessageState;
import ru.shakurov.jlmq.jlmq_server.entity.Queue;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByState(MessageState state);

    Optional<Message> findByMessageId(String messageId);

    Optional<Message> findFirstByStateAndQueueOrderByCreatedAtAsc(MessageState state, Queue queue);

}
