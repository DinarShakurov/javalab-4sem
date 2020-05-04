package ru.shakurov.websocket_chat.services;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public interface ChatService {
    void connectSession(WebSocketSession session);

    void disconnectSession(WebSocketSession session, CloseStatus closeStatus);

    void handleMessage(WebSocketSession session, TextMessage textMessage);
}
