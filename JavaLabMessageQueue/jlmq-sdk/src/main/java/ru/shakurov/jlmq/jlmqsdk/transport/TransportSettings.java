package ru.shakurov.jlmq.jlmqsdk.transport;

public interface TransportSettings {

    String connectionUrl();

    String subscriptionUrl();

    String sendingUrl();
}
