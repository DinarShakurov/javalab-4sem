package ru.shakurov.jlmq.jlmq_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shakurov.jlmq.jlmq_server.entity.Consumer;
import ru.shakurov.jlmq.jlmq_server.entity.ConsumerState;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsumerDto {
    private Long id;
    private String username;
    private String sessionId;
    private ConsumerState state;
    private String queueName;

    public static ConsumerDto from(Consumer consumer) {
        return ConsumerDto.builder()
                .id(consumer.getId())
                .username(consumer.getUsername())
                .state(consumer.getState())
                .queueName(consumer.getQueue().getName())
                .sessionId(consumer.getSessionId())
                .build();
    }

    public static List<ConsumerDto> from(List<Consumer> consumers) {
        return consumers.stream()
                .map(ConsumerDto::from)
                .collect(Collectors.toList());
    }
}
