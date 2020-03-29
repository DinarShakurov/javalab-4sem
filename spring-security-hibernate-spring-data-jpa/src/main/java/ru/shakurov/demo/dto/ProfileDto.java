package ru.shakurov.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shakurov.demo.models.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDto {
    private String name;
    private String email;

    public static ProfileDto from(User user) {
        return ProfileDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
