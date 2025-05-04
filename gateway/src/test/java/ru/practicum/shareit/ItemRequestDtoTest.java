package ru.practicum.shareit;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ItemRequestDtoTest {

    private Validator validator;
    private ItemRequestDto itemRequestDto;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(1L);
        itemRequestDto.setDescription("Test Description");
        itemRequestDto.setCreated(LocalDateTime.now());
        itemRequestDto.setRequester(new ItemRequestDto.Requester());
    }

    @Test
    void validItemRequestDto_noViolations() {
        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(itemRequestDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void blankDescription_validationFails() {
        itemRequestDto.setDescription("");
        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(itemRequestDto);
        assertEquals(1, violations.size());
        assertEquals("Описание запроса не может быть пустым", violations.iterator().next().getMessage());
    }

    @Test
    void nullDescription_validationFails() {
        itemRequestDto.setDescription(null);
        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(itemRequestDto);
        assertEquals(1, violations.size());
        assertEquals("Описание запроса не может быть пустым", violations.iterator().next().getMessage());
    }
}
