package ru.shakurov.jlmq.jlmqsdk.objects;

public interface JlmqProducerBuilder {
    JlmqProducerBuilder toQueue(String queueName);

    JlmqProducer create();
}
