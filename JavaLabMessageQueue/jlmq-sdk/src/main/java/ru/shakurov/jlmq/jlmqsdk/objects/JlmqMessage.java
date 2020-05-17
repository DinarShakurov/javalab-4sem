package ru.shakurov.jlmq.jlmqsdk.objects;

import ru.shakurov.jlmq.jlmqsdk.objects.impl.MessageDto;

public interface JlmqMessage {
    void accepted();

    void completed();

    Object getBody();

    MessageDto.MessageCommand getCommand();

    String getQueueName();

    String getMessageId();
}
