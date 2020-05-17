package ru.shakurov.jlmq.jlmqsdk.objects.impl;

import lombok.Getter;
import ru.shakurov.jlmq.jlmqsdk.objects.JlmqConsumer;
import ru.shakurov.jlmq.jlmqsdk.transport.MessageHandler;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportSession;

public class DefaultJlmqConsumer implements JlmqConsumer {

    @Getter
    private final MessageHandler handler;
    @Getter
    private final String queueName;
    private final TransportSession session;

    public DefaultJlmqConsumer(TransportSession session, String queueName, MessageHandler handler) {
        this.session = session;
        this.queueName = queueName;
        this.handler = handler;
    }
}
