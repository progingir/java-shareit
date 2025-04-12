package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

@Data
public class ItemWithBookingsResponse {
    private Long id;
    private String name;
    private String description;
    private boolean available;
    private BookingDto lastBooking; // Изменено с LocalDateTime на BookingDto
    private BookingDto nextBooking; // Изменено с LocalDateTime на BookingDto
    private List<CommentDto> comments;

    public ItemWithBookingsResponse(Long id, String name, String description, boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
    }
}