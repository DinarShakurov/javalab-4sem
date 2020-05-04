package ru.shakurov.websocket_chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shakurov.websocket_chat.models.Message;
import ru.shakurov.websocket_chat.models.Room;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long roomId;
    private List<Message> messages;

    public static RoomDto from(Room room) {
        return RoomDto.builder()
                .messages(room.getMessages())
                .roomId(room.getId())
                .build();
    }
}
