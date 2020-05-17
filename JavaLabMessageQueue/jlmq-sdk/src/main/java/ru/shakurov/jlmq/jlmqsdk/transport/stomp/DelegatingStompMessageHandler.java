package ru.shakurov.jlmq.jlmqsdk.transport.stomp;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.util.Assert;
import ru.shakurov.jlmq.jlmqsdk.objects.impl.DefaultJlmqMessage;
import ru.shakurov.jlmq.jlmqsdk.objects.impl.MessageDto;
import ru.shakurov.jlmq.jlmqsdk.transport.DelegatingMessageHandler;
import ru.shakurov.jlmq.jlmqsdk.transport.MessageHandler;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportSession;

import java.lang.reflect.Type;

public class DelegatingStompMessageHandler implements StompFrameHandler, DelegatingMessageHandler {

    private final TransportSession transportSession;

    private MessageHandler messageHandler;

    public DelegatingStompMessageHandler(TransportSession transportSession) {
        this.transportSession = transportSession;
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return MessageDto.class;
    }

    @Override
    public void delegateTo(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Assert.notNull(payload, "Payload is null");
        Assert.isAssignable(payload.getClass(), MessageDto.class, "Type mismatch");
        DefaultJlmqMessage message = new DefaultJlmqMessage(transportSession, (MessageDto) payload);
        messageHandler.handleMessage(message);
    }

}
