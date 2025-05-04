package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BookingTest {

    @Test
    void testBooking() {
        Booking booking = new Booking();
        booking.setId(1L);
        Item item = new Item();
        item.setId(1L);
        booking.setItem(item);
        User booker = new User();
        booker.setId(1L);
        booking.setBooker(booker);
        LocalDateTime start = LocalDateTime.now();
        booking.setStart(start);
        LocalDateTime end = start.plusDays(1);
        booking.setEnd(end);
        booking.setStatus(BookingStatus.APPROVED);

        assertEquals(1L, booking.getId());
        assertEquals(item, booking.getItem());
        assertEquals(booker, booking.getBooker());
        assertEquals(start, booking.getStart());
        assertEquals(end, booking.getEnd());
        assertEquals(BookingStatus.APPROVED, booking.getStatus());
    }

    @Test
    void testBookingWithNulls() {
        Booking booking = new Booking();
        assertNull(booking.getId());
        assertNull(booking.getItem());
        assertNull(booking.getBooker());
        assertNull(booking.getStart());
        assertNull(booking.getEnd());
        assertNull(booking.getStatus());
    }

    @Test
    void testBookingStatusEnum() {
        assertEquals("WAITING", BookingStatus.WAITING.name());
        assertEquals("APPROVED", BookingStatus.APPROVED.name());
        assertEquals("REJECTED", BookingStatus.REJECTED.name());
    }
}