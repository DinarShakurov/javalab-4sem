package ru.shakurov.jlmq.jlmqsdk.transport.web_socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import ru.shakurov.jlmq.jlmqsdk.transport.AbstractTransportSession;
import ru.shakurov.jlmq.jlmqsdk.transport.MessageHandler;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportSettings;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class WebSocketTransportSessionAdapter extends AbstractTransportSession {

    private final WebSocketClient webSocketClient;
    private final WebSocketSession webSocketSession;
    private final ObjectMapper mapper;

    private final DelegatingWebSocketMessageHandler handler;

    public WebSocketTransportSessionAdapter(WebSocketClient webSocketClient, TransportSettings settings) {
        try {
            this.mapper = new ObjectMapper();
            this.handler = new DelegatingWebSocketMessageHandler(this, mapper);
            this.webSocketClient = webSocketClient;
            this.webSocketSession = webSocketClient.doHandshake(handler, settings.connectionUrl()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void onReceive(MessageHandler handler) {
        this.handler.delegateTo(handler);
    }

    @Override
    protected void processMessageSending(Object message) {
        try {
            String text = mapper.writeValueAsString(message);
            webSocketSession.sendMessage(new TextMessage(text));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
