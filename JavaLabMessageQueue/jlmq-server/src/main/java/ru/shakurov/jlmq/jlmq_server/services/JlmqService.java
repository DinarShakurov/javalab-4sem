package ru.shakurov.jlmq.jlmq_server.services;

import ru.shakurov.jlmq.jlmq_server.dto.ConsumerDto;
import ru.shakurov.jlmq.jlmq_server.dto.MessageDto;

import java.util.List;

public interface JlmqService {
    void handleJlmqMessage(MessageDto messageDto, String username, String sessionId);

    List<ConsumerDto> getInactiveConsumers();

    List<MessageDto> getAwaitingMessages();

    boolean isPresentAwaitingMessageFor(ConsumerDto consumerDto);

    MessageDto makeReceiveMessageFor(ConsumerDto consumerDto);

    void receiveMessageSentFor(ConsumerDto consumerDto);
}
