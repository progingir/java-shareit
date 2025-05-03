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

    @JsonProperty("requester")
    private Requester requester;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime created;

    private List<ItemDto> items;

    @Data
    public static class ItemDto {
        private Long id;
        private String name;
        private String description;
        private Boolean available;
        private Owner owner;
        private Long requestId;
    }

    @Data
    public static class Requester {
        private Long id;
        private String email;
        private String name;
    }

    @Data
    public static class Owner {
        private Long id;
        private String email;
        private String name;
    }
}