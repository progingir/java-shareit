package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewUserRequest {
    @NotBlank(message = "validation.user.name.notBlank")
    private String name;
    @NotBlank(message = "validation.user.email.notBlank")
    @Email(message = "validation.user.email.invalid")
    private String email;
}