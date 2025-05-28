package ru.practicum.shareit.booking.event;

import org.springframework.context.ApplicationEvent;
import ru.practicum.shareit.booking.Booking;

public class BookingCreatedEvent extends ApplicationEvent {
    private final Booking booking;

    public BookingCreatedEvent(Object source, Booking booking) {
        super(source);
        this.booking = booking;
    }

    public Booking getBooking() {
        return booking;
    }
}