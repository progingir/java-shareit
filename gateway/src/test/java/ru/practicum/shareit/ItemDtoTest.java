package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.dto.ItemDto;
import ru.practicum.shareit.request.dto.OwnerDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ItemDtoTest {

    @Test
    void testItemDto() {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("Item");
        itemDto.setDescription("Description");
        itemDto.setAvailable(true);
        itemDto.setRequestId(2L);

        OwnerDto owner = new OwnerDto();
        owner.setId(1L);
        owner.setName("Owner");
        owner.setEmail("owner@example.com");
        itemDto.setOwner(owner);

        assertEquals(1L, itemDto.getId());
        assertEquals("Item", itemDto.getName());
        assertEquals("Description", itemDto.getDescription());
        assertEquals(true, itemDto.getAvailable());
        assertEquals(2L, itemDto.getRequestId());
        assertEquals(owner, itemDto.getOwner());
    }

    @Test
    void testItemDtoWithNulls() {
        ItemDto itemDto = new ItemDto();
        assertNull(itemDto.getId());
        assertNull(itemDto.getName());
        assertNull(itemDto.getDescription());
        assertNull(itemDto.getAvailable());
        assertNull(itemDto.getRequestId());
        assertNull(itemDto.getOwner());
    }
}
