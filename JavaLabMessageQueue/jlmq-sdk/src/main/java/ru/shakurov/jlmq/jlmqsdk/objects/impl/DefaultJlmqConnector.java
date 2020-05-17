package ru.shakurov.jlmq.jlmqsdk.objects.impl;

import ru.shakurov.jlmq.jlmqsdk.objects.*;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportSession;

public class DefaultJlmqConnector implements JlmqConnector {
    private final TransportSession session;

    public DefaultJlmqConnector(TransportSession session) {
        this.session = session;
    }

    @Override
    public JlmqConsumerBuilder consumer() {
        return new DefaultJlmqConsumerBuilder(session);
    }

    @Override
    public JlmqProducerBuilder producer() {
         return new DefaultJlmqProducerBuilder(session);
    }
}
