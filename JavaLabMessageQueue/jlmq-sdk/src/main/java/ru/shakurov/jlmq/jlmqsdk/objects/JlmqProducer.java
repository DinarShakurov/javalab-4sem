package ru.shakurov.jlmq.jlmqsdk.objects;

public interface JlmqProducer {
    void send(String body);

    String getQueueName();
}
