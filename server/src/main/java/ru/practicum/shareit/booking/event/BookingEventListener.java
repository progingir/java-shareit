package ru.practicum.shareit.booking.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookingEventListener {

    @EventListener
    public void handleBookingCreatedEvent(BookingCreatedEvent event) {
        log.info("Новое бронирование создано: ID={}, ItemID={}, BookerID={}",
                event.getBooking().getId(),
                event.getBooking().getItem().getId(),
                event.getBooking().getBooker().getId());
    }
}