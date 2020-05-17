package ru.shakurov.jlmq.jlmqsdk.objects.impl;

import ru.shakurov.jlmq.jlmqsdk.objects.JlmqConnector;
import ru.shakurov.jlmq.jlmqsdk.objects.JlmqConnectorBuilder;
import ru.shakurov.jlmq.jlmqsdk.transport.DefaultTransportSettings;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportClient;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportClientProvider;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportSession;

public class DefaultJlmqConnectroBuilder implements JlmqConnectorBuilder {
    private final TransportClientProvider transportClientProvider;

    private String port;
    private boolean withStomp = false;

    public DefaultJlmqConnectroBuilder(TransportClientProvider provider) {
        transportClientProvider = provider;
    }

    @Override
    public JlmqConnectorBuilder port(String port) {
        this.port = port;
        return this;
    }

    @Override
    public JlmqConnectorBuilder withStomp() {
        this.withStomp = true;
        return this;
    }

    @Override
    public JlmqConnector connect() {
        TransportClient client = getTransportClientInstance();
        TransportSession session = client.connect(new DefaultTransportSettings(port));
        return new DefaultJlmqConnector(session);
    }

    private TransportClient getTransportClientInstance() {
        return transportClientProvider.provide(withStomp);
    }
}
