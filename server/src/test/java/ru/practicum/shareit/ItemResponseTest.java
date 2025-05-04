package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.BookingShortDto;
import ru.practicum.shareit.item.dto.ItemResponse;
import ru.practicum.shareit.item.dto.ItemWithBookingsResponse;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ItemResponseTest {

    @Test
    void testItemResponse() {
        ItemResponse response = new ItemResponse(
                1L, "Item", "Description", true, null, null, Collections.emptyList()
        );
        assertEquals(1L, response.getId());
        assertEquals("Item", response.getName());
        assertEquals("Description", response.getDescription());
        assertEquals(true, response.isAvailable());
        assertNull(response.getLastBooking());
        assertNull(response.getNextBooking());
        assertEquals(Collections.emptyList(), response.getComments());
    }

    @Test
    void testItemWithBookingsResponse() {
        ItemWithBookingsResponse response = new ItemWithBookingsResponse(
                1L, "Item", "Description", true
        );
        response.setLastBooking(new BookingShortDto());
        response.setNextBooking(new BookingShortDto());
        response.setComments(Collections.emptyList());

        assertEquals(1L, response.getId());
        assertEquals("Item", response.getName());
        assertEquals("Description", response.getDescription());
        assertEquals(true, response.isAvailable());
        assertEquals(new BookingShortDto(), response.getLastBooking());
        assertEquals(new BookingShortDto(), response.getNextBooking());
        assertEquals(Collections.emptyList(), response.getComments());
    }

    @Test
    void testItemWithBookingsResponseWithNulls() {
        ItemWithBookingsResponse response = new ItemWithBookingsResponse(
                null, null, null, false
        );
        assertNull(response.getId());
        assertNull(response.getName());
        assertNull(response.getDescription());
        assertEquals(false, response.isAvailable());
        assertNull(response.getLastBooking());
        assertNull(response.getNextBooking());
        assertNull(response.getComments());
    }
}
