package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.BookingShortDto;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemResponse {
    private Long id;
    private String name;
    private String description;
    private boolean available;
    private BookingShortDto lastBooking;
    private BookingShortDto nextBooking;
    private List<CommentDto> comments;
}