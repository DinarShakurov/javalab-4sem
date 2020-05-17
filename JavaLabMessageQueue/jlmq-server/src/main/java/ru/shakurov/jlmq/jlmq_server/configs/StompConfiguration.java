package ru.shakurov.jlmq.jlmq_server.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ru.shakurov.jlmq.jlmq_server.resolver.SessionIdMethodArgumentResolver;
import ru.shakurov.jlmq.jlmq_server.resolver.UsernameMethodArgumentResolver;

import java.util.List;

@Profile("stomp")
@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class StompConfiguration implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private UsernameMethodArgumentResolver usernameMethodArgumentResolver;
    @Autowired private SessionIdMethodArgumentResolver sessionIdMethodArgumentResolver;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // endpoint с которым работают клиенты, они могут посылать сюда сообщения
        // они могут их отсюда получать
        registry.enableSimpleBroker("/jlmq");
        //registry.enableSimpleBroker("/echo");
        // сюда можно направлять сообщения, чтобы они попали в само приложение
        //registry.setApplicationDestinationPrefixes("/jlmq");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // сюда можно подключаться вебсокет-клиентам (stomp-клиент)
        registry.addEndpoint("/web-socket").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(usernameMethodArgumentResolver);
        argumentResolvers.add(sessionIdMethodArgumentResolver);
    }
}
