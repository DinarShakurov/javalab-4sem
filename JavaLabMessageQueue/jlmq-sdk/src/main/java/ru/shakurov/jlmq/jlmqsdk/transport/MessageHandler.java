package ru.shakurov.jlmq.jlmqsdk.transport;

import ru.shakurov.jlmq.jlmqsdk.objects.JlmqMessage;

@FunctionalInterface
public interface MessageHandler {

    void handleMessage(JlmqMessage message);
}
