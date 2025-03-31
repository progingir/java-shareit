package ru.practicum.shareit.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    private String email;
    private String name;

    public User(User other) {
        this.id = other.getId();
        this.email = other.getEmail();
        this.name = other.getName();
    }
}