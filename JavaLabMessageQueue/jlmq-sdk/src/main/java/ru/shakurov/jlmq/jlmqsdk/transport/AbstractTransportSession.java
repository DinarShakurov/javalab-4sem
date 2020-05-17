package ru.shakurov.jlmq.jlmqsdk.transport;

import ru.shakurov.jlmq.jlmqsdk.objects.impl.MessageDto;

public abstract class AbstractTransportSession implements TransportSession {

    @Override
    public void send(String queueName, String body) {
        MessageDto message = MessageDto.builder()
                .command(MessageDto.MessageCommand.SEND)
                .queueName(queueName)
                .body(body)
                .build();
        processMessageSending(message);
        System.out.println("send");
    }

    @Override
    public void subscribe(String queueName) {
        MessageDto message = MessageDto.builder()
                .command(MessageDto.MessageCommand.SUBSCRIBE)
                .queueName(queueName)
                .build();
        processMessageSending(message);
        System.out.println("subscribe");
    }

    @Override
    public void accept(String messageId) {
        MessageDto message = MessageDto.builder()
                .command(MessageDto.MessageCommand.ACCEPTED)
                .messageId(messageId)
                .build();
        processMessageSending(message);
        System.out.println("accept");
    }

    @Override
    public void complete(String messageId) {
        MessageDto message = MessageDto.builder()
                .command(MessageDto.MessageCommand.COMPLETED)
                .messageId(messageId)
                .build();
        processMessageSending(message);
        System.out.println("complete");
    }

    public abstract void onReceive(MessageHandler handler);

    protected abstract void processMessageSending(Object message);

}
