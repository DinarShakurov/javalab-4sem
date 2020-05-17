package ru.shakurov.jlmq.jlmqsdk.objects.impl;

import ru.shakurov.jlmq.jlmqsdk.objects.JlmqProducer;
import ru.shakurov.jlmq.jlmqsdk.objects.JlmqProducerBuilder;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportSession;

public class DefaultJlmqProducerBuilder implements JlmqProducerBuilder {
    private final TransportSession transportSession;
    private String queueName;

    public DefaultJlmqProducerBuilder(TransportSession transportSession) {
        this.transportSession = transportSession;
    }

    @Override
    public JlmqProducerBuilder toQueue(String queueName) {
        this.queueName = queueName;
        return this;
    }

    @Override
    public JlmqProducer create() {
        return new DefaultJlmqProducer(transportSession, queueName);
    }
}
