package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ItemRequestTest {

    @Test
    void testItemRequest() {
        ItemRequest request = new ItemRequest();
        request.setId(1L);
        request.setDescription("Need a drill");
        User requestor = new User();
        requestor.setId(1L);
        request.setRequestor(requestor);
        LocalDateTime now = LocalDateTime.now();
        request.setCreated(now);

        assertEquals(1L, request.getId());
        assertEquals("Need a drill", request.getDescription());
        assertEquals(requestor, request.getRequestor());
        assertEquals(now, request.getCreated());
    }

    @Test
    void testItemRequestWithNulls() {
        ItemRequest request = new ItemRequest();
        assertNull(request.getId());
        assertNull(request.getDescription());
        assertNull(request.getRequestor());
        assertNull(request.getCreated());
    }
}