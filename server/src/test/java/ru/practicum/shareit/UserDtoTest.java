package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserDtoTest {

    @Test
    void testNewUserRequest() {
        NewUserRequest request = new NewUserRequest("John", "john@example.com");
        assertEquals("John", request.getName());
        assertEquals("john@example.com", request.getEmail());

        request.setName("Jane");
        request.setEmail("jane@example.com");
        assertEquals("Jane", request.getName());
        assertEquals("jane@example.com", request.getEmail());
    }

    @Test
    void testNewUserRequestWithNulls() {
        NewUserRequest request = new NewUserRequest(null, null);
        assertNull(request.getName());
        assertNull(request.getEmail());
    }

    @Test
    void testUpdateUserRequest() {
        UpdateUserRequest request = new UpdateUserRequest("John", "john@example.com");
        assertEquals("John", request.getName());
        assertEquals("john@example.com", request.getEmail());

        request.setName(null);
        request.setEmail(null);
        assertNull(request.getName());
        assertNull(request.getEmail());
    }

    @Test
    void testUserResponse() {
        UserResponse response = new UserResponse(1L, "John", "john@example.com");
        assertEquals(1L, response.getId());
        assertEquals("John", response.getName());
        assertEquals("john@example.com", response.getEmail());

        response.setId(2L);
        response.setName("Jane");
        response.setEmail("jane@example.com");
        assertEquals(2L, response.getId());
        assertEquals("Jane", response.getName());
        assertEquals("jane@example.com", response.getEmail());
    }
}