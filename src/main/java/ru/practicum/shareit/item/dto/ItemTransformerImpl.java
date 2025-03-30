package ru.practicum.shareit.item.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

@Component
public class ItemTransformerImpl implements ItemTransformer {

    @Override
    public ItemResponse toResponse(Item item) {
        return new ItemResponse(item.getId(), item.getName(), item.getDescription(), item.getStatus());
    }

    @Override
    public Item toItem(NewItemRequest request) {
        Item item = new Item();
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setStatus(request.getStatus());
        return item;
    }

    @Override
    public Item applyUpdates(UpdateItemRequest updates, Item item) {
        if (updates.getName() != null) item.setName(updates.getName());
        if (updates.getDescription() != null) item.setDescription(updates.getDescription());
        if (updates.getStatus() != null) item.setStatus(updates.getStatus());
        return item;
    }
}