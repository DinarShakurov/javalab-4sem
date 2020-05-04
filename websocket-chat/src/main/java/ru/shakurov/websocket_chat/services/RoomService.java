package ru.shakurov.websocket_chat.services;

import ru.shakurov.websocket_chat.dto.RoomDto;
import ru.shakurov.websocket_chat.forms.RoomForm;
import ru.shakurov.websocket_chat.models.Room;

import java.util.List;

public interface RoomService {
    List<Room> getRooms();

    void createRoom(RoomForm roomForm);

    RoomDto getRoom(Long roomId);
}
