package ru.shakurov.jlmq.jlmqsdk;

import ru.shakurov.jlmq.jlmqsdk.objects.JlmqConnectorBuilder;
import ru.shakurov.jlmq.jlmqsdk.objects.impl.DefaultJlmqConnectroBuilder;
import ru.shakurov.jlmq.jlmqsdk.transport.DefaultTransportClientProvider;

public abstract class Jlmq {

    public static JlmqConnectorBuilder connector() {
        return new DefaultJlmqConnectroBuilder(new DefaultTransportClientProvider());
    }

}
