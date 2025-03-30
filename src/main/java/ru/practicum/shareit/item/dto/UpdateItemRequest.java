package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.model.ItemStatus;

@Data
@AllArgsConstructor
public class UpdateItemRequest {
    private String name;
    private String description;
    private ItemStatus status;

    @JsonCreator
    public UpdateItemRequest(@JsonProperty("name") String name,
                             @JsonProperty("description") String description,
                             @JsonProperty("available") Boolean isAvailable) {
        this.name = name;
        this.description = description;
        this.status = isAvailable == null ? null : (isAvailable ? ItemStatus.AVAILABLE : ItemStatus.UNAVAILABLE);
    }
}