package ru.shakurov.jlmq.jlmq_server.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.shakurov.jlmq.jlmq_server.dto.ConsumerDto;

public class InactiveConsumerJobFoundEvent extends ApplicationEvent {

    @Getter
    private final ConsumerDto changedConsumerDto;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public InactiveConsumerJobFoundEvent(Object source, ConsumerDto consumerDto) {
        super(source);
        this.changedConsumerDto = consumerDto;
    }
}

