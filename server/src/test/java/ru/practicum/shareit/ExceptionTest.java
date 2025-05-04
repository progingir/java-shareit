package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.ForbiddenAccessException;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionTest {

    @Test
    void testUserNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException("User not found");
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testItemNotFoundException() {
        ItemNotFoundException exception = new ItemNotFoundException("Item not found");
        assertEquals("Item not found", exception.getMessage());
    }

    @Test
    void testForbiddenAccessException() {
        ForbiddenAccessException exception = new ForbiddenAccessException("Access denied");
        assertEquals("Access denied", exception.getMessage());
    }
}