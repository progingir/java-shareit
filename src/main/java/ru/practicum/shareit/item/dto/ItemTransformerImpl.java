package ru.practicum.shareit.item.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Component
public class ItemTransformerImpl implements ItemTransformer {

    @Override
    public ItemResponse toResponse(Item item) {
        return new ItemResponse(item.getId(), item.getName(), item.getDescription(), item.isAvailable(), List.of());
    }

    @Override
    public ItemResponse toResponse(Item item, List<CommentDto> comments) {
        return new ItemResponse(item.getId(), item.getName(), item.getDescription(), item.isAvailable(), comments);
    }

    @Override
    public ItemWithBookingsResponse toResponseWithBookingsAndComments(Item item, BookingDto lastBooking, BookingDto nextBooking, List<CommentDto> comments) {
        ItemWithBookingsResponse response = new ItemWithBookingsResponse(
                item.getId(), item.getName(), item.getDescription(), item.isAvailable()
        );
        response.setLastBooking(lastBooking);
        response.setNextBooking(nextBooking);
        response.setComments(comments);
        return response;
    }

    @Override
    public Item toItem(NewItemRequest request) {
        Item item = new Item();
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setAvailable(request.getAvailable() != null && request.getAvailable());
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