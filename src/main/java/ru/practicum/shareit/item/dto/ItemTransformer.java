package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemTransformer {
    ItemResponse toResponse(Item item);

    ItemResponse toResponse(Item item, List<CommentDto> comments);

    ItemWithBookingsResponse toResponseWithBookingsAndComments(Item item, BookingDto lastBooking, BookingDto nextBooking, List<CommentDto> comments);

    Item toItem(NewItemRequest request);

    Item applyUpdates(UpdateItemRequest request, Item item);
}