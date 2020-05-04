package ru.shakurov.websocket_chat.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.shakurov.websocket_chat.dto.MessageDto;
import ru.shakurov.websocket_chat.dto.UserDto;
import ru.shakurov.websocket_chat.models.Message;
import ru.shakurov.websocket_chat.models.Room;
import ru.shakurov.websocket_chat.models.User;
import ru.shakurov.websocket_chat.repositories.MessageRepository;
import ru.shakurov.websocket_chat.repositories.RoomRepository;
import ru.shakurov.websocket_chat.repositories.UserRepository;
import ru.shakurov.websocket_chat.services.ChatService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private Environment environment;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private MessageRepository messageRepository;

    private static final List<WebSocketSession> listSessions = new CopyOnWriteArrayList<>();

    @Override
    public void connectSession(WebSocketSession session) {
        listSessions.add(session);
    }

    @Override
    public void disconnectSession(WebSocketSession session, CloseStatus closeStatus) {
        listSessions.remove(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, TextMessage textMessage) {
        try {
            Map<String, Object> attributes = session.getAttributes();
            UserDto userDto = (UserDto) attributes.get("userDto");

            MessageDto messageDto = objectMapper.readValue(textMessage.getPayload(), MessageDto.class);
            messageDto.setFrom(userDto.getLogin());

            Long roomId = (Long) attributes.get(environment.getRequiredProperty("chat.room.attribute.name"));
            User user = userRepository.getOne(userDto.getId());
            Room room = roomRepository.getOne(roomId);

            Message message = Message.builder()
                    .message(messageDto.getText())
                    .user(user)
                    .room(room)
                    .time(LocalDateTime.now())
                    .build();
            messageRepository.save(message);

            sendMessage(messageDto);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void sendMessage(MessageDto messageDto) {
        listSessions.forEach(webSocketSession -> {
            try {
                webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageDto)));
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        });
    }
}
