package ru.shakurov.jlmq.jlmq_server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.shakurov.jlmq.jlmq_server.annotation.SessionId;
import ru.shakurov.jlmq.jlmq_server.annotation.Username;
import ru.shakurov.jlmq.jlmq_server.dto.ConsumerDto;
import ru.shakurov.jlmq.jlmq_server.dto.MessageDto;
import ru.shakurov.jlmq.jlmq_server.events.InactiveConsumerJobFoundEvent;
import ru.shakurov.jlmq.jlmq_server.services.JlmqService;
import ru.shakurov.jlmq.jlmq_server.utils.HeadersUtil;

@Profile("stomp")
@Controller
public class JlmqStompController {
    @Autowired
    private JlmqService jlmqService;
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/jlmq")
    public void handleJlmqMessage(@Payload MessageDto messageDto, @SessionId String sessionId, @Username String username) {
        System.out.println(messageDto.getCommand());
        jlmqService.handleJlmqMessage(messageDto, sessionId, username);
    }

    @EventListener
    public void handleIdleConsumer(InactiveConsumerJobFoundEvent event) {
        ConsumerDto targetConsumer = event.getChangedConsumerDto();
        MessageDto messageDto = jlmqService.makeReceiveMessageFor(targetConsumer);
        template.convertAndSendToUser(
                targetConsumer.getUsername(),
                "/jlmq",
                messageDto,
                HeadersUtil.createHeaders(targetConsumer.getSessionId())
        );
        jlmqService.receiveMessageSentFor(targetConsumer);
    }
}
