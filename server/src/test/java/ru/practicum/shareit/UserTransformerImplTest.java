package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserResponse;
import ru.practicum.shareit.user.dto.UserTransformerImpl;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.*;

class UserTransformerImplTest {

    private final UserTransformerImpl transformer = new UserTransformerImpl();

    @Test
    void testToResponse() {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setEmail("john@example.com");

        UserResponse response = transformer.toResponse(user);
        assertEquals(1L, response.getId());
        assertEquals("John", response.getName());
        assertEquals("john@example.com", response.getEmail());
    }

    @Test
    void testToUser() {
        NewUserRequest request = new NewUserRequest("John", "john@example.com");
        User user = transformer.toUser(request);
        assertEquals("John", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertNull(user.getId());
    }

    @Test
    void testApplyUpdatesWithAllFields() {
        User user = new User();
        user.setName("Old Name");
        user.setEmail("old@example.com");

        UpdateUserRequest updates = new UpdateUserRequest("New Name", "new@example.com");
        User updatedUser = transformer.applyUpdates(updates, user);

        assertEquals("New Name", updatedUser.getName());
        assertEquals("new@example.com", updatedUser.getEmail());
    }

    @Test
    void testApplyUpdatesWithPartialFields() {
        User user = new User();
        user.setName("Old Name");
        user.setEmail("old@example.com");

        UpdateUserRequest updates = new UpdateUserRequest(null, "new@example.com");
        User updatedUser = transformer.applyUpdates(updates, user);

        assertEquals("Old Name", updatedUser.getName());
        assertEquals("new@example.com", updatedUser.getEmail());
    }

    @Test
    void testApplyUpdatesWithNoChanges() {
        User user = new User();
        user.setName("Old Name");
        user.setEmail("old@example.com");

        UpdateUserRequest updates = new UpdateUserRequest(null, null);
        User updatedUser = transformer.applyUpdates(updates, user);

        assertEquals("Old Name", updatedUser.getName());
        assertEquals("old@example.com", updatedUser.getEmail());
    }
}