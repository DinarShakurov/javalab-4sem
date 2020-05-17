package ru.shakurov.jlmq.jlmq_server.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import ru.shakurov.jlmq.jlmq_server.annotation.SessionId;

@Component
public class SessionIdMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SessionId.class) && parameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, Message<?> message) {
        return SimpMessageHeaderAccessor.getSessionId(message.getHeaders());
    }
}
