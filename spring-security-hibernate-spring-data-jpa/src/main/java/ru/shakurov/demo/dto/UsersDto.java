package ru.shakurov.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shakurov.demo.models.Role;
import ru.shakurov.demo.models.State;
import ru.shakurov.demo.models.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersDto {
    private String email;
    private String name;
    private State state;
    private Role role;

    public static UsersDto from(User user) {
        return UsersDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .state(user.getState())
                .build();
    }
}
