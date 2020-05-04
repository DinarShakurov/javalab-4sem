package ru.shakurov.websocket_chat.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.WebUtils;
import ru.shakurov.websocket_chat.services.SecurityService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@Component
public class ChatHandshakeInterceptor implements HandshakeInterceptor {
    @Autowired
    private Environment environment;
    @Autowired
    private SecurityService securityService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        try {
            HttpServletRequest httpRequest = ((ServletServerHttpRequest) request).getServletRequest();
            Cookie cookie = WebUtils.getCookie(httpRequest, environment.getRequiredProperty("security.token.name"));
            Assert.notNull(cookie, "Auth cookie doesn't exist");

            Long id = Long.valueOf(Objects.requireNonNull(WebUtils.findParameterValue(httpRequest, "room")));
            attributes.put(environment.getProperty("chat.room.attribute.name"), id);
            attributes.put("userDto", securityService.authorizeByToken(cookie.getValue()));

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
