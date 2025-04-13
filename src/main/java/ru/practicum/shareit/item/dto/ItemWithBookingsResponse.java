package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.booking.BookingShortDto;

import java.util.List;

@Data
public class ItemWithBookingsResponse {
    private Long id;
    private String name;
    private String description;
    private boolean available;
    private BookingShortDto lastBooking;
    private BookingShortDto nextBooking;
    private List<CommentDto> comments;

    public ItemWithBookingsResponse(Long id, String name, String description, boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
    }
}