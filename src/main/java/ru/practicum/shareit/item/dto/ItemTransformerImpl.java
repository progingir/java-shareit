package ru.practicum.shareit.item.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

@Component
public class ItemTransformerImpl implements ItemTransformer {

    @Override
    public ItemResponse toResponse(Item item) {
        return new ItemResponse(item.getId(), item.getName(), item.getDescription(), item.isAvailable());
    }

    @Override
    public Item toItem(NewItemRequest request) {
        Item item = new Item();
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setAvailable(request.getAvailable() != null && request.getAvailable()); // Учитываем null
        return item;
    }

    @Override
    public Item applyUpdates(UpdateItemRequest updates, Item item) {
        if (updates.getName() != null) item.setName(updates.getName());
        if (updates.getDescription() != null) item.setDescription(updates.getDescription());
        if (updates.getAvailable() != null) item.setAvailable(updates.getAvailable());
        return item;
    }
}