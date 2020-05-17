package ru.shakurov.jlmq.jlmqsdk.objects.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    private String messageId;
    private MessageCommand command;
    private String queueName;
    private Object body;

    public enum MessageCommand {
        @JsonProperty("send") SEND,
        @JsonProperty("subscribe") SUBSCRIBE,
        @JsonProperty("receive") RECEIVE,
        @JsonProperty("accepted") ACCEPTED,
        @JsonProperty("completed") COMPLETED
    }
}
