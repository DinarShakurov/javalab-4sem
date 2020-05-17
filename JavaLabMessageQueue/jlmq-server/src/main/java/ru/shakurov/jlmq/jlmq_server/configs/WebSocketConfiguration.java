package ru.shakurov.jlmq.jlmq_server.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import ru.shakurov.jlmq.jlmq_server.handlers.JlmqWebSocketHandler;

@Profile("web-socket")
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Autowired private JlmqWebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(webSocketHandler)
                .setHandshakeHandler(defaultHandshakeHandler())
                .withSockJS();
    }

    @Bean
    public HandshakeHandler defaultHandshakeHandler(){
        return new DefaultHandshakeHandler();
    }
}
