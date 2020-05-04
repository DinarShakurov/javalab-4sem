package ru.shakurov.websocket_chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shakurov.websocket_chat.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
