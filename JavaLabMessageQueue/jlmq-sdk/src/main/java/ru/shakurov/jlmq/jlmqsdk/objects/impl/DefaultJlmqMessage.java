package ru.shakurov.jlmq.jlmqsdk.objects.impl;

import ru.shakurov.jlmq.jlmqsdk.objects.JlmqMessage;
import ru.shakurov.jlmq.jlmqsdk.transport.TransportSession;

public class DefaultJlmqMessage implements JlmqMessage {
    private final TransportSession session;
    private final MessageDto messageDto;

    public DefaultJlmqMessage(TransportSession session, MessageDto messageDto) {
        this.session = session;
        this.messageDto = messageDto;
    }

    @Override
    public void accepted() {
        session.accept(getMessageId());
    }

    @Override
    public void completed() {
        session.complete(getMessageId());
    }

    @Override
    public Object getBody() {
        return messageDto.getBody();
    }

    @Override
    public MessageDto.MessageCommand getCommand() {
        return messageDto.getCommand();
    }

    @Override
    public String getQueueName() {
        return messageDto.getQueueName();
    }

    @Override
    public String getMessageId() {
        return messageDto.getMessageId();
    }
}
