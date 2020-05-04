package ru.shakurov.websocket_chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Principal {
    private Long id;
    private String login;

    @Override
    public String getName() {
        return login;
    }
}
