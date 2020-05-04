package ru.shakurov.websocket_chat.services.impl;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ru.shakurov.websocket_chat.dto.UserDto;
import ru.shakurov.websocket_chat.forms.SignInForm;
import ru.shakurov.websocket_chat.models.User;
import ru.shakurov.websocket_chat.repositories.UserRepository;
import ru.shakurov.websocket_chat.services.SecurityService;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Environment environment;

    @Override
    public String createToken(SignInForm signInForm) {
        User user = userRepository.findUserByLogin(signInForm.getLogin()).orElseThrow(IllegalArgumentException::new);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("login", user.getLogin())
                .signWith(SignatureAlgorithm.HS256, environment.getRequiredProperty("jwt.secret"))
                .compact();
    }

    @Override
    public UserDto authorizeByToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(environment.getRequiredProperty("jwt.secret"))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return UserDto.builder()
                .id(Long.parseLong(claims.getSubject()))
                .login(claims.get("login", String.class))
                .build();
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
             Jwts.parser()
                    .setSigningKey(environment.getRequiredProperty("jwt.secret"))
                    .parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;

    }


}
