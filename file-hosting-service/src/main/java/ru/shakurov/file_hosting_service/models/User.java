package ru.shakurov.file_hosting_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String email;
    private String password;
    private String name;
    private Integer id;
    private String link;
    private String state;
}
