package ru.shakurov.jlmq.jlmqsdk.transport;

public interface TransportClient {

    TransportSession connect(TransportSettings settings);
}
