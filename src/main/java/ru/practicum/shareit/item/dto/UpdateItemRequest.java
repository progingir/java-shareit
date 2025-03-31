package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateItemRequest {
    private String name;
    private String description;
    private Boolean available;

    @JsonCreator
    public UpdateItemRequest(@JsonProperty("name") String name,
                             @JsonProperty("description") String description,
                             @JsonProperty("available") Boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
    }
}