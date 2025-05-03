package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareit.booking.BookingStatus;

import java.time.LocalDateTime;

@Data
public class BookingShortDto {
    private Long id;

    @NotNull(message = "Время начала бронирования не может быть пустым")
    @FutureOrPresent(message = "Время начала бронирования не может быть в прошлом")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime start;

    @NotNull(message = "Время окончания бронирования не может быть пустым")
    @Future(message = "Время окончания бронирования должно быть в будущем")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime end;

    @NotNull(message = "ID вещи не может быть пустым")
    private Long itemId;

    private Long bookerId;

    private BookingStatus status;
}