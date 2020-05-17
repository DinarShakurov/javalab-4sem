package ru.shakurov.jlmq.jlmqsdk.transport;

public interface DelegatingMessageHandler {

    void delegateTo(MessageHandler handler);

}
