package ru.shakurov.jlmq.jlmq_server.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.support.MissingSessionUserException;
import org.springframework.messaging.simp.user.DestinationUserNameProvider;
import org.springframework.stereotype.Component;
import ru.shakurov.jlmq.jlmq_server.annotation.Username;

import java.security.Principal;

@Component
public class UsernameMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(Username.class) &&
                methodParameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, Message<?> message) throws Exception {
        MessageHeaders headers = message.getHeaders();
        String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);

        Principal principal = SimpMessageHeaderAccessor.getUser(headers);
        if (principal != null) {
            return  (principal instanceof DestinationUserNameProvider ?
                    ((DestinationUserNameProvider) principal).getDestinationUserName() : principal.getName());
        }
        if (sessionId == null) {
            throw new MissingSessionUserException(message);
        }
        return sessionId;
    }
}
