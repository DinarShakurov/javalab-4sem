package ru.shakurov.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users_2")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String hashPassword;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private State state;
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
