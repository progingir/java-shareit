//package ru.practicum.shareit;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.practicum.shareit.item.dto.UpdateItemRequest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UpdateItemRequestTest {
//
//    private ObjectMapper objectMapper;
//    private UpdateItemRequest updateItemRequest;
//
//    @BeforeEach
//    void setUp() {
//        objectMapper = new ObjectMapper();
//        updateItemRequest = new UpdateItemRequest("Updated Item", "Updated Description", false);
//    }
//
//    @Test
//    void partialUpdate_noViolations() {
//        UpdateItemRequest partialUpdate = new UpdateItemRequest(null, "New Description", null);
//        assertNull(partialUpdate.getName());
//        assertEquals("New Description", partialUpdate.getDescription());
//        assertNull(partialUpdate.getAvailable());
//    }
//
//    @Test
//    void serializeAndDeserialize_success() throws Exception {
//        String json = objectMapper.writeValueAsString(updateItemRequest);
//        UpdateItemRequest deserialized = objectMapper.readValue(json, UpdateItemRequest.class);
//
//        assertNotNull(deserialized);
//        assertEquals(updateItemRequest.getName(), deserialized.getName());
//        assertEquals(updateItemRequest.getDescription(), deserialized.getDescription());
//        assertEquals(updateItemRequest.getAvailable(), deserialized.getAvailable());
//    }
//}