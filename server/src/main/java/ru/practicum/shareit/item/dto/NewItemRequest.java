package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewItemRequest {
    private String name;
    private String description;
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