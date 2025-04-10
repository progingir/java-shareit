package ru.practicum.shareit.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Item item;
    private Booker booker;
    private String status;

    @Data
    public static class Item {
        private Long id;
        private String name;
    }

    @Data
    public static class Booker {
        private Long id;
    }
}