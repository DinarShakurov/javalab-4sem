package ru.shakurov.jlmq.jlmqsdk.transport.web_socket;

import org.springframework.web.socket.client.WebSocketClient;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportClient;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportSession;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportSettings;

public class WebSocketTransportClientAdapter implements TransportClient {

    private final WebSocketClient webSocketClient;

    public WebSocketTransportClientAdapter(WebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    @Override
    public TransportSession connect(TransportSettings settings) {
        return new WebSocketTransportSessionAdapter(webSocketClient, settings);
    }

}
