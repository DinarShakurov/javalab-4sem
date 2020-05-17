package ru.shakurov.jlmq.jlmqsdk.transport.web_socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.shakurov.jlmq.jlmqsdk.objects.impl.DefaultJlmqMessage;
import ru.shakurov.jlmq.jlmqsdk.objects.impl.MessageDto;
import ru.shakurov.jlmq.jlmqsdk.transport.DelegatingMessageHandler;
import ru.shakurov.jlmq.jlmqsdk.transport.MessageHandler;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportSession;

public class DelegatingWebSocketMessageHandler extends TextWebSocketHandler implements DelegatingMessageHandler {
    private final TransportSession transportSession;
    private final ObjectMapper mapper;

    private MessageHandler messageHandler;

    public DelegatingWebSocketMessageHandler(TransportSession transportSession, ObjectMapper mapper) {
        this.transportSession = transportSession;
        this.mapper = mapper;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        MessageDto jlmqMessage = mapper.readValue(message.getPayload(), MessageDto.class);
        messageHandler.handleMessage(new DefaultJlmqMessage(transportSession, jlmqMessage));
    }

    @Override
    public void delegateTo(MessageHandler handler) {
        this.messageHandler = handler;
    }
}
