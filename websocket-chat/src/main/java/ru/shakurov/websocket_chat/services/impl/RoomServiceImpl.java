package ru.shakurov.websocket_chat.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shakurov.websocket_chat.dto.RoomDto;
import ru.shakurov.websocket_chat.forms.RoomForm;
import ru.shakurov.websocket_chat.models.Room;
import ru.shakurov.websocket_chat.repositories.RoomRepository;
import ru.shakurov.websocket_chat.services.RoomService;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    @Override
    public void createRoom(RoomForm roomForm) {
        Room room = Room.builder()
                .name(roomForm.getName())
                .build();
        roomRepository.save(room);
    }

    @Override
    @Transactional
    public RoomDto getRoom(Long roomId) {
        Room room = roomRepository.getOne(roomId);
        return RoomDto.from(room);
    }
}
