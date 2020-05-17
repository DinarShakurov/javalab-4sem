package ru.shakurov.jlmq.jlmqsdk.transport;

public interface TransportSession {
    void send(String queueName, String body);

    void subscribe(String queueName);

    void accept(String messageId);

    void complete(String messageId);

    void onReceive(MessageHandler handler);
}

