package ru.shakurov.websocket_chat.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import ru.shakurov.websocket_chat.handlers.ChatHandshakeInterceptor;
import ru.shakurov.websocket_chat.handlers.WebSocketMessagesHandler;

@Configuration
public class WebSocketConfiguration implements WebSocketConfigurer {
    @Autowired
    private WebSocketMessagesHandler handler;

    @Autowired
    private ChatHandshakeInterceptor interceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(handler, "/chat")
                .setHandshakeHandler(new DefaultHandshakeHandler())
                .addInterceptors(interceptor)
                .withSockJS()
                .setWebSocketEnabled(true);
    }
}

