package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemTransformer {
    ItemResponse toResponse(Item item);

    ItemResponse toResponse(Item item, List<CommentDto> comments); // Added for findItemById

    ItemWithBookingsResponse toResponseWithBookingsAndComments(Item item, LocalDateTime lastBooking, LocalDateTime nextBooking, List<CommentDto> comments); // Added for fetchUserItems

    Item toItem(NewItemRequest request);

    Item applyUpdates(UpdateItemRequest request, Item item);
}