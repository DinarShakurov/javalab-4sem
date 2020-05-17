package ru.shakurov.jlmq.jlmqsdk.objects;

import ru.shakurov.jlmq.jlmqsdk.transport.MessageHandler;

public interface JlmqConsumerBuilder {
    JlmqConsumerBuilder subscribe(String queueName);

    JlmqConsumerBuilder onReceive(MessageHandler handler);

    JlmqConsumer create();
}
