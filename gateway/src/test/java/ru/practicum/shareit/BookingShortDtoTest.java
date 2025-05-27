package ru.practicum.shareit;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingShortDto;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookingShortDtoTest {

    private Validator validator;
    private BookingShortDto bookingShortDto;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        bookingShortDto = new BookingShortDto();
        bookingShortDto.setId(1L);
        bookingShortDto.setStart(LocalDateTime.now().plusDays(1));
        bookingShortDto.setEnd(LocalDateTime.now().plusDays(2));
        bookingShortDto.setItemId(1L);
        bookingShortDto.setBookerId(2L);
    }

    @Test
    void validBookingShortDto_noViolations() {
        Set<ConstraintViolation<BookingShortDto>> violations = validator.validate(bookingShortDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void nullStartDate_validationFails() {
        bookingShortDto.setStart(null);
        Set<ConstraintViolation<BookingShortDto>> violations = validator.validate(bookingShortDto);
        assertEquals(1, violations.size());
        assertEquals("Время начала бронирования не может быть пустым", violations.iterator().next().getMessage());
    }

    @Test
    void pastStartDate_validationFails() {
        bookingShortDto.setStart(LocalDateTime.now().minusDays(1));
        Set<ConstraintViolation<BookingShortDto>> violations = validator.validate(bookingShortDto);
        assertEquals(1, violations.size());
        assertEquals("Время начала бронирования не может быть в прошлом", violations.iterator().next().getMessage());
    }

    @Test
    void nullEndDate_validationFails() {
        bookingShortDto.setEnd(null);
        Set<ConstraintViolation<BookingShortDto>> violations = validator.validate(bookingShortDto);
        assertEquals(1, violations.size());
        assertEquals("Время окончания бронирования не может быть пустым", violations.iterator().next().getMessage());
    }

    @Test
    void pastEndDate_validationFails() {
        bookingShortDto.setEnd(LocalDateTime.now().minusDays(1));
        Set<ConstraintViolation<BookingShortDto>> violations = validator.validate(bookingShortDto);
        assertEquals(1, violations.size());
        assertEquals("Время окончания бронирования должно быть в будущем", violations.iterator().next().getMessage());
    }

    @Test
    void nullItemId_validationFails() {
        bookingShortDto.setItemId(null);
        Set<ConstraintViolation<BookingShortDto>> violations = validator.validate(bookingShortDto);
        assertEquals(1, violations.size());
        assertEquals("ID вещи не может быть пустым", violations.iterator().next().getMessage());
    }
}