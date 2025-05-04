package ru.practicum.shareit.request.dto;

import lombok.Data;

@Data
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private OwnerDto owner;
    private Long requestId;
}