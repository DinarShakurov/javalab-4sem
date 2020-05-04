package ru.shakurov.websocket_chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shakurov.websocket_chat.models.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
