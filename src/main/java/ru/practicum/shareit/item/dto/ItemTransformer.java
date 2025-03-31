package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

public interface ItemTransformer {
    ItemResponse toResponse(Item item);

    Item toItem(NewItemRequest request);

    Item applyUpdates(UpdateItemRequest updates, Item item);
}