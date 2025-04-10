package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDto {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime start;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime end;

    private ItemDto itemId;
    private BookerDto bookerId;
    private String status;

    @Data
    public static class ItemDto {
        private Long id;
        private String name;
    }

    @Data
    public static class BookerDto {
        private Long id;
        private String name;
    }
}