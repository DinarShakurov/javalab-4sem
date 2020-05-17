package ru.shakurov.jlmq.jlmqsdk.transport.stomp;

import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import ru.shakurov.jlmq.jlmqsdk.transport.AbstractTransportSession;
import ru.shakurov.jlmq.jlmqsdk.transport.MessageHandler;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportSettings;

import java.util.concurrent.ExecutionException;

public class StompTransportSessionAdapter extends AbstractTransportSession {
    private final StompSession stompSession;
    private final TransportSettings transportSettings;
    private final StompSessionHandler connectionHandler;

    private final DelegatingStompMessageHandler frameHandler;

    public StompTransportSessionAdapter(WebSocketStompClient stompClient, TransportSettings settings) {
        try {
            this.connectionHandler = new ConnectionHandler();
            this.frameHandler = new DelegatingStompMessageHandler(this);
            this.stompSession = stompClient.connect(settings.connectionUrl(), connectionHandler).get();
            this.stompSession.subscribe(settings.subscriptionUrl(), frameHandler);
            this.transportSettings = settings;
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void onReceive(MessageHandler handler) {
        this.frameHandler.delegateTo(handler);
    }

    @Override
    protected void processMessageSending(Object message) {
        stompSession.send(transportSettings.sendingUrl(), message);
    }

}
