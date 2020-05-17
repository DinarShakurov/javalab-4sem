package ru.shakurov.jlmq.jlmq_server.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shakurov.jlmq.jlmq_server.annotation.QueueStateChanges;
import ru.shakurov.jlmq.jlmq_server.dto.ConsumerDto;
import ru.shakurov.jlmq.jlmq_server.dto.MessageDto;
import ru.shakurov.jlmq.jlmq_server.entity.*;
import ru.shakurov.jlmq.jlmq_server.repository.ConsumerRepository;
import ru.shakurov.jlmq.jlmq_server.repository.MessageRepository;
import ru.shakurov.jlmq.jlmq_server.repository.QueueRepository;
import ru.shakurov.jlmq.jlmq_server.services.JlmqService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class JlmqServiceImpl implements JlmqService {
    @Autowired
    private ConsumerRepository consumerRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private QueueRepository queueRepository;

    @Override
    @QueueStateChanges
    @Transactional
    public void handleJlmqMessage(MessageDto messageDto, String username, String sessionId) {
        MessageDto.MessageCommand command = messageDto.getCommand();
        switch (command) {
            case SEND -> send(messageDto);
            case ACCEPTED -> accepted(messageDto, username);
            case COMPLETED -> completed(messageDto, username);
            case SUBSCRIBE -> subscribe(messageDto, username, sessionId);
            default -> throw new IllegalStateException("Command \"" + command.name().toUpperCase() + "\" is not supported");
        }
    }

    @Override
    public List<ConsumerDto> getInactiveConsumers() {
        return ConsumerDto.from(consumerRepository.findByState(ConsumerState.INACTIVE));
    }

    @Override
    public List<MessageDto> getAwaitingMessages() {
        return MessageDto.from(messageRepository.findByState(MessageState.AWAITING));
    }

    @Override
    public boolean isPresentAwaitingMessageFor(ConsumerDto consumerDto) {
        List<MessageDto> awaitingMessages = getAwaitingMessages();
        for (MessageDto awaitingMessage : awaitingMessages) {
            if (consumerDto.getQueueName().equals(awaitingMessage.getQueueName()))
                return true;
        }
        return false;
    }

    @Override
    public MessageDto makeReceiveMessageFor(ConsumerDto consumerDto) {
        Queue queue = queueRepository
                .findByName(consumerDto.getQueueName())
                .orElseThrow();
        Message message = messageRepository
                .findFirstByStateAndQueueOrderByCreatedAtAsc(MessageState.AWAITING, queue)
                .orElseThrow();
        return MessageDto.builder()
                .messageId(message.getMessageId())
                .queueName(message.getQueue().getName())
                .command(MessageDto.MessageCommand.RECEIVE)
                .body(message.getBody())
                .build();
    }

    @Override
    @QueueStateChanges
    @Transactional
    public void receiveMessageSentFor(ConsumerDto consumerDto) {
        Consumer consumer = consumerRepository.findByUsername(consumerDto.getUsername()).orElseThrow();
        consumer.setState(ConsumerState.ACCEPTING);
        consumerRepository.save(consumer);
    }


    private void subscribe(MessageDto messageDto, String username, String sessionId) {
        Queue queue = queueRepository.findByName(messageDto.getQueueName()).orElseThrow();
        Consumer consumer = Consumer.builder()
                .queue(queue)
                .state(ConsumerState.INACTIVE)
                .username(username)
                .sessionId(sessionId)
                .build();
        consumerRepository.save(consumer);
    }

    private void completed(MessageDto messageDto, String username) {
        String messageId = messageDto.getMessageId();
        Message message = messageRepository.findByMessageId(messageId).orElseThrow();
        message.setState(MessageState.COMPLETED);
        messageRepository.save(message);

        Consumer consumer = consumerRepository.findByUsername(username).orElseThrow();
        consumer.setState(ConsumerState.INACTIVE);
        consumerRepository.save(consumer);
    }

    private void accepted(MessageDto messageDto, String username) {
        String messageId = messageDto.getMessageId();
        Message message = messageRepository.findByMessageId(messageId).orElseThrow();
        message.setState(MessageState.ACCEPTED);
        messageRepository.save(message);

        Consumer consumer = consumerRepository.findByUsername(username).orElseThrow();
        consumer.setState(ConsumerState.WORKING);
        consumerRepository.save(consumer);
    }

    private void send(MessageDto messageDto) {
        Queue queue = queueRepository.findByName(messageDto.getQueueName()).orElseThrow();
        String body = messageDto.getBody() == null ? "" : (String) messageDto.getBody();
        Message message = Message.builder()
                .messageId(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .queue(queue)
                .body(body)
                .state(MessageState.AWAITING)
                .build();
        messageRepository.save(message);
    }
}
