package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemTransformer {
    ItemResponse toResponse(Item item);

    ItemResponse toResponse(Item item, List<CommentDto> comments, Long userId);

    ItemWithBookingsResponse toResponseWithBookingsAndComments(
            Item item,
            BookingShortDto lastBooking,
            BookingShortDto nextBooking,
            List<CommentDto> comments
    );

    Item toItem(NewItemRequest request);

    Item applyUpdates(UpdateItemRequest request, Item item);
}