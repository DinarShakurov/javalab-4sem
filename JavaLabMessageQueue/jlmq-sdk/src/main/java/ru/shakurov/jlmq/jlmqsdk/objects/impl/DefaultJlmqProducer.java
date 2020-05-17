package ru.shakurov.jlmq.jlmqsdk.objects.impl;

import lombok.Getter;
import ru.shakurov.jlmq.jlmqsdk.objects.JlmqProducer;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportSession;

public class DefaultJlmqProducer implements JlmqProducer {
    @Getter
    private final String queueName;
    private final TransportSession transportSession;

    public DefaultJlmqProducer(TransportSession transportSession, String queueName) {
        this.transportSession = transportSession;
        this.queueName = queueName;
    }

    @Override
    public void send(String body) {
        this.transportSession.send(queueName, body);
    }
}
