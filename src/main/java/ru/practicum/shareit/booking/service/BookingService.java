package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {
    BookingDto createBooking(BookingDto bookingDto, Long userId);

    BookingDto updateBooking(Long bookingId, Long userId, Boolean approved);

    BookingDto getBooking(Long bookingId, Long userId);

    List<BookingDto> getUserBookings(Long userId, String state);

    List<BookingDto> getOwnerBookings(Long userId, String state);
}