package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.model.ItemStatus;

@Data
@AllArgsConstructor
public class ItemResponse {
    private Long id;
    private String name;
    private String description;
    private ItemStatus status;

    @JsonProperty("available")
    public boolean isAvailable() {
        return status == ItemStatus.AVAILABLE;
    }
}