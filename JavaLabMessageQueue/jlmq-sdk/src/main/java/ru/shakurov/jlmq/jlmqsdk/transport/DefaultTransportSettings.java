package ru.shakurov.jlmq.jlmqsdk.transport;

public class DefaultTransportSettings implements TransportSettings {
    private String port;
    private String subscriptionUrl = "/user/jlmq";
    private String destinationUrl = "/jlmq";

    public DefaultTransportSettings(String port) {
        this.port = port;
    }

    @Override
    public String connectionUrl() {
        return "http://localhost:" + port + "/web-socket";
    }

    @Override
    public String subscriptionUrl() {
        return subscriptionUrl;
    }

    @Override
    public String sendingUrl() {
        return destinationUrl;
    }
}
