//package ru.practicum.shareit;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.Validation;
//import jakarta.validation.Validator;
//import jakarta.validation.ValidatorFactory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.practicum.shareit.item.dto.NewItemRequest;
//
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class NewItemRequestTest {
//
//    private Validator validator;
//    private ObjectMapper objectMapper;
//    private NewItemRequest newItemRequest;
//
//    @BeforeEach
//    void setUp() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//        objectMapper = new ObjectMapper();
//
//        newItemRequest = new NewItemRequest("Test Item", "Test Description", true, null);
//    }
//
//    @Test
//    void validNewItemRequest_noViolations() {
//        Set<ConstraintViolation<NewItemRequest>> violations = validator.validate(newItemRequest);
//        assertTrue(violations.isEmpty());
//    }
//
//    @Test
//    void blankName_validationFails() {
//        newItemRequest.setName("");
//        Set<ConstraintViolation<NewItemRequest>> violations = validator.validate(newItemRequest);
//        assertEquals(1, violations.size());
//        assertEquals("Название не может быть пустым", violations.iterator().next().getMessage());
//    }
//
//    @Test
//    void nullDescription_validationFails() {
//        newItemRequest.setDescription(null);
//        Set<ConstraintViolation<NewItemRequest>> violations = validator.validate(newItemRequest);
//        assertEquals(1, violations.size());
//        assertEquals("Описание не может быть пустым", violations.iterator().next().getMessage());
//    }
//
//    @Test
//    void nullAvailable_validationFails() {
//        newItemRequest.setAvailable(null);
//        Set<ConstraintViolation<NewItemRequest>> violations = validator.validate(newItemRequest);
//        assertEquals(1, violations.size());
//        assertEquals("Доступность должна быть указана", violations.iterator().next().getMessage());
//    }
//
//    @Test
//    void serializeAndDeserialize_success() throws Exception {
//        String json = objectMapper.writeValueAsString(newItemRequest);
//        NewItemRequest deserialized = objectMapper.readValue(json, NewItemRequest.class);
//
//        assertNotNull(deserialized);
//        assertEquals(newItemRequest.getName(), deserialized.getName());
//        assertEquals(newItemRequest.getDescription(), deserialized.getDescription());
//        assertEquals(newItemRequest.getAvailable(), deserialized.getAvailable());
//        assertEquals(newItemRequest.getRequestId(), deserialized.getRequestId());
//    }
//}
