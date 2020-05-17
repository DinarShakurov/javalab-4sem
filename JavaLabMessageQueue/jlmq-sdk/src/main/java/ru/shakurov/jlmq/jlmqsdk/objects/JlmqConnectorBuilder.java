package ru.shakurov.jlmq.jlmqsdk.objects;

public interface JlmqConnectorBuilder {
    JlmqConnectorBuilder port(String port);

    JlmqConnectorBuilder withStomp();

    JlmqConnector connect();
}
