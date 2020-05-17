package ru.shakurov.jlmq.jlmq_server.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.shakurov.jlmq.jlmq_server.dto.ConsumerDto;
import ru.shakurov.jlmq.jlmq_server.dto.MessageDto;
import ru.shakurov.jlmq.jlmq_server.events.InactiveConsumerJobFoundEvent;
import ru.shakurov.jlmq.jlmq_server.services.JlmqService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Profile("web-socket")
@Component
public class JlmqWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private JlmqService jlmqService;
    @Autowired
    private ObjectMapper objectMapper;

    private final Map<String, WebSocketSession> webSocketSessionMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        webSocketSessionMap.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        MessageDto messageDto = objectMapper.readValue(message.getPayload(), MessageDto.class);
        String sessionId = session.getId();
        String username = session.getPrincipal() == null ?
                session.getId() : session.getPrincipal().getName();
        jlmqService.handleJlmqMessage(messageDto, username, sessionId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        webSocketSessionMap.remove(session.getId());
    }

    @EventListener
    public void handleInactiveConsumer(InactiveConsumerJobFoundEvent event) {
        ConsumerDto consumerDto = event.getChangedConsumerDto();
        MessageDto messageDto = jlmqService.makeReceiveMessageFor(consumerDto);

        WebSocketSession session = webSocketSessionMap.get(consumerDto.getSessionId());
        if (session != null) {
            try {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageDto)));
                jlmqService.receiveMessageSentFor(consumerDto);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
