package ru.shakurov.jlmq.jlmqsdk.objects.impl;

import ru.shakurov.jlmq.jlmqsdk.objects.JlmqConsumer;
import ru.shakurov.jlmq.jlmqsdk.objects.JlmqConsumerBuilder;
import ru.shakurov.jlmq.jlmqsdk.transport.MessageHandler;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportSession;

public class DefaultJlmqConsumerBuilder implements JlmqConsumerBuilder {
    private final TransportSession session;
    private String queueName;
    private MessageHandler messageHandler;

    public DefaultJlmqConsumerBuilder(TransportSession session) {
        this.session = session;
    }

    @Override
    public JlmqConsumerBuilder subscribe(String queueName) {
        this.queueName = queueName;
        return this;
    }

    @Override
    public JlmqConsumerBuilder onReceive(MessageHandler handler) {
        this.messageHandler = handler;
        return this;
    }

    @Override
    public JlmqConsumer create() {
        session.onReceive(messageHandler);
        session.subscribe(queueName);
        return new DefaultJlmqConsumer(session, queueName, messageHandler);
    }
}
