package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.model.ItemStatus;

@Data
@AllArgsConstructor
public class ItemResponse {
    private Long itemId;
    private String title;
    private String details;
    private ItemStatus availability;

    @JsonProperty("available")
    public boolean isAvailable() {
        return availability == ItemStatus.AVAILABLE;
    }
}