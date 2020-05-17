package ru.shakurov.jlmq.jlmqsdk.objects;

public interface JlmqConnector {
    JlmqConsumerBuilder consumer();

    JlmqProducerBuilder producer();
}
