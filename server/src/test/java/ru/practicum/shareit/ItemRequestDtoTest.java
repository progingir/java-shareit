package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.dto.ItemDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.OwnerDto;
import ru.practicum.shareit.request.dto.RequesterDto;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ItemRequestDtoTest {

    @Test
    void testItemRequestDto() {
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(1L);
        dto.setDescription("Need a drill");
        RequesterDto requester = new RequesterDto();
        requester.setId(1L);
        dto.setRequester(requester);
        dto.setCreated(LocalDateTime.now());
        dto.setItems(Collections.emptyList());

        assertEquals(1L, dto.getId());
        assertEquals("Need a drill", dto.getDescription());
        assertEquals(requester, dto.getRequester());
        assertEquals(Collections.emptyList(), dto.getItems());
    }

    @Test
    void testItemDto() {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("Drill");
        itemDto.setDescription("Powerful drill");
        itemDto.setAvailable(true);
        OwnerDto owner = new OwnerDto();
        owner.setId(1L);
        itemDto.setOwner(owner);
        itemDto.setRequestId(1L);

        assertEquals(1L, itemDto.getId());
        assertEquals("Drill", itemDto.getName());
        assertEquals("Powerful drill", itemDto.getDescription());
        assertTrue(itemDto.getAvailable());
        assertEquals(owner, itemDto.getOwner());
        assertEquals(1L, itemDto.getRequestId());
    }

    @Test
    void testOwnerDto() {
        OwnerDto owner = new OwnerDto();
        owner.setId(1L);
        owner.setEmail("john@example.com");
        owner.setName("John");

        assertEquals(1L, owner.getId());
        assertEquals("john@example.com", owner.getEmail());
        assertEquals("John", owner.getName());
    }

    @Test
    void testRequesterDto() {
        RequesterDto requester = new RequesterDto();
        requester.setId(1L);
        requester.setEmail("john@example.com");
        requester.setName("John");

        assertEquals(1L, requester.getId());
        assertEquals("john@example.com", requester.getEmail());
        assertEquals("John", requester.getName());
    }
}
