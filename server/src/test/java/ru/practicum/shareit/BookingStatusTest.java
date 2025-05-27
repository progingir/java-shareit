package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.BookingStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingStatusTest {

    @Test
    void testBookingStatusValues() {
        BookingStatus[] expected = {BookingStatus.WAITING, BookingStatus.APPROVED, BookingStatus.REJECTED};
        assertEquals(3, BookingStatus.values().length);
        assertEquals(expected[0], BookingStatus.WAITING);
        assertEquals(expected[1], BookingStatus.APPROVED);
        assertEquals(expected[2], BookingStatus.REJECTED);
    }

    @Test
    void testBookingStatusValueOf() {
        assertEquals(BookingStatus.WAITING, BookingStatus.valueOf("WAITING"));
        assertEquals(BookingStatus.APPROVED, BookingStatus.valueOf("APPROVED"));
        assertEquals(BookingStatus.REJECTED, BookingStatus.valueOf("REJECTED"));
    }
}
