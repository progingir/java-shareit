package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewItemRequest {
    @NotBlank(message = "Название не может быть пустым")
    private String name;

    @NotBlank(message = "Описание не может быть пустым")
    private String description;

    @NotNull(message = "Доступность должна быть указана")
    private Boolean available;

    private Long requestId;

    @JsonCreator
    public NewItemRequest(@JsonProperty("name") String name,
                          @JsonProperty("description") String description,
                          @JsonProperty("available") Boolean available,
                          @JsonProperty("requestId") Long requestId) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.requestId = requestId;
    }
}