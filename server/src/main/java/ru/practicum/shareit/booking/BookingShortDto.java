package ru.practicum.shareit.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingShortDto {
    private Long id;

    @NotNull(message = "validation.booking.start.notNull")
    @FutureOrPresent(message = "validation.booking.start.futureOrPresent")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime start;

    @NotNull(message = "validation.booking.end.notNull")
    @Future(message = "validation.booking.end.future")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime end;

    @NotNull(message = "validation.booking.itemId.notNull")
    private Long itemId;

    private Long bookerId;

    private BookingStatus status;
}