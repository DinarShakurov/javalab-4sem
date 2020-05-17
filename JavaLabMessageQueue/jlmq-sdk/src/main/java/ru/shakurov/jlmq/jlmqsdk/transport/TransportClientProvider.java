package ru.shakurov.jlmq.jlmqsdk.transport;

public interface TransportClientProvider {
    TransportClient provide(boolean withStomp);
}
