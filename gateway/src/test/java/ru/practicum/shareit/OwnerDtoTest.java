package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.dto.OwnerDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OwnerDtoTest {

    @Test
    void testOwnerDto() {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(1L);
        ownerDto.setName("Owner");
        ownerDto.setEmail("owner@example.com");

        assertEquals(1L, ownerDto.getId());
        assertEquals("Owner", ownerDto.getName());
        assertEquals("owner@example.com", ownerDto.getEmail());
    }

    @Test
    void testOwnerDtoWithNulls() {
        OwnerDto ownerDto = new OwnerDto();
        assertNull(ownerDto.getId());
        assertNull(ownerDto.getName());
        assertNull(ownerDto.getEmail());
    }
}