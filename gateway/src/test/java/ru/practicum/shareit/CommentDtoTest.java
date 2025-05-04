//package ru.practicum.shareit;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.Validation;
//import jakarta.validation.Validator;
//import jakarta.validation.ValidatorFactory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.practicum.shareit.item.dto.CommentDto;
//
//import java.time.LocalDateTime;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class CommentDtoTest {
//
//    private Validator validator;
//    private ObjectMapper objectMapper;
//    private CommentDto commentDto;
//
//    @BeforeEach
//    void setUp() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//        objectMapper = new ObjectMapper();
//
//        commentDto = new CommentDto();
//        commentDto.setId(1L);
//        commentDto.setText("Test Comment");
//        commentDto.setItemId(1L);
//        commentDto.setAuthorId(1L);
//        commentDto.setAuthorName("Test User");
//        commentDto.setCreated(LocalDateTime.now());
//    }
//
//    @Test
//    void validCommentDto_noViolations() {
//        Set<ConstraintViolation<CommentDto>> violations = validator.validate(commentDto);
//        assertTrue(violations.isEmpty());
//    }
//}