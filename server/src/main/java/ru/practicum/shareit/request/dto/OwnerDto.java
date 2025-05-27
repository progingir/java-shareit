package ru.practicum.shareit.request.dto;

import lombok.Data;

@Data
public class OwnerDto {
    private Long id;
    private String email;
    private String name;
}
