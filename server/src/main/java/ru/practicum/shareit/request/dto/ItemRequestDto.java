package ru.practicum.shareit.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemRequestDto {
    private Long id;

    @NotBlank(message = "Описание запроса не может быть пустым")
    private String description;

    @JsonProperty("name")
    private String name;

    private Long requestorId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime created;

    private List<ItemDto> items;

    @Data
    public static class ItemDto {
        private Long id;
        private String name;
        private Long ownerId;
    }
}