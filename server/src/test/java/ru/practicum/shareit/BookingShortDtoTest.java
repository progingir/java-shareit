package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.BookingShortDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BookingShortDtoTest {

    @Test
    void testBookingShortDto() {
        BookingShortDto dto = new BookingShortDto();
        dto.setId(1L);
        dto.setBookerId(1L);
        dto.setStart(LocalDateTime.now().plusDays(1));
        dto.setEnd(LocalDateTime.now().plusDays(2));

        assertEquals(1L, dto.getId());
        assertEquals(1L, dto.getBookerId());
        assertEquals(LocalDateTime.now().plusDays(1).withNano(0), dto.getStart().withNano(0));
        assertEquals(LocalDateTime.now().plusDays(2).withNano(0), dto.getEnd().withNano(0));
    }

    @Test
    void testBookingShortDtoWithNulls() {
        BookingShortDto dto = new BookingShortDto();
        assertNull(dto.getId());
        assertNull(dto.getBookerId());
        assertNull(dto.getStart());
        assertNull(dto.getEnd());
    }
}
