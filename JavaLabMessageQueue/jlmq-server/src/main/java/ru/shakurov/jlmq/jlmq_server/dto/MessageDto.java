package ru.shakurov.jlmq.jlmq_server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import ru.shakurov.jlmq.jlmq_server.entity.Message;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    @Nullable
    private String messageId;
    private MessageCommand command;
    private String queueName;
    @Nullable
    private Object body;

    public enum MessageCommand {
        @JsonProperty("send") SEND,
        @JsonProperty("subscribe") SUBSCRIBE,
        @JsonProperty("receive") RECEIVE,
        @JsonProperty("accepted") ACCEPTED,
        @JsonProperty("completed") COMPLETED
    }

    public static MessageDto from(Message message) {
        return MessageDto.builder()
                .messageId(message.getMessageId())
                .body(message.getBody())
                .queueName(message.getQueue().getName())
                .build();
    }

    public static List<MessageDto> from(List<Message> consumers) {
        return consumers.stream()
                .map(MessageDto::from)
                .collect(Collectors.toList());
    }
}
