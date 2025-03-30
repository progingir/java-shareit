package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareit.item.model.ItemStatus;

@Data
public class NewItemRequest {
    @NotBlank(message = "Название не может быть пустым")
    private String name;
    @NotBlank(message = "Описание не может быть пустым")
    private String description;
    @NotNull(message = "Статус должен быть указан")
    private ItemStatus status;

    @JsonCreator
    public NewItemRequest(@JsonProperty("name") String name,
                          @JsonProperty("description") String description,
                          @JsonProperty("available") Boolean isAvailable) {
        this.name = name;
        this.description = description;
        this.status = isAvailable == null ? null : (isAvailable ? ItemStatus.AVAILABLE : ItemStatus.UNAVAILABLE);
    }
}