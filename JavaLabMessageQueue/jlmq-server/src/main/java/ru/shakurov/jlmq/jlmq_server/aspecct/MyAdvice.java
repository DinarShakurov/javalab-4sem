package ru.shakurov.jlmq.jlmq_server.aspecct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ru.shakurov.jlmq.jlmq_server.events.InactiveConsumerJobFoundEvent;
import ru.shakurov.jlmq.jlmq_server.services.JlmqService;

@Aspect
@Component
public class MyAdvice {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private JlmqService jlmqService;

    @Around("@annotation(ru.shakurov.jlmq.jlmq_server.annotation.QueueStateChanges)")
    public Object handleQueueStateChangesAnn(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        handleInactiveConsumers();
        return proceed;
    }

    private void handleInactiveConsumers() {
        jlmqService.getInactiveConsumers()
                .stream()
                .filter(consumerDto -> jlmqService.isPresentAwaitingMessageFor(consumerDto))
                .forEach(consumerDto -> applicationEventPublisher.publishEvent(new InactiveConsumerJobFoundEvent(this, consumerDto)));
    }
}
