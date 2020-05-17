package ru.shakurov.jlmq.jlmqsdk.objects;

import ru.shakurov.jlmq.jlmqsdk.transport.MessageHandler;

public interface JlmqConsumer {
    String getQueueName();

    MessageHandler getHandler();
}
