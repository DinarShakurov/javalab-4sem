package ru.shakurov.jlmq.jlmqsdk.transport.stomp;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportClient;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportSession;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportSettings;

public class StompTransportClientAdapter implements TransportClient {

    private final WebSocketStompClient stompClient;

    public StompTransportClientAdapter(WebSocketStompClient stompClient) {
        this.stompClient = stompClient;
        this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        this.stompClient.setTaskScheduler(new DefaultManagedTaskScheduler());
    }

    @Override
    public TransportSession connect(TransportSettings settings) {
        return new StompTransportSessionAdapter(stompClient, settings);
    }

}
