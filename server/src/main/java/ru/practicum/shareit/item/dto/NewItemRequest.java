package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewItemRequest {
    @NotBlank(message = "validation.item.name.notBlank")
    private String name;

    @NotBlank(message = "validation.item.description.notBlank")
    private String description;

    @NotNull(message = "validation.item.available.notNull")
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