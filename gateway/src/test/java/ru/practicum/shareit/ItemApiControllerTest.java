package ru.practicum.shareit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.ItemApiController;
import ru.practicum.shareit.item.ItemClient;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemApiController.class)
class ItemApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemClient itemClient;

    @Autowired
    private ObjectMapper objectMapper;

    private NewItemRequest newItemRequest;
    private UpdateItemRequest updateItemRequest;
    private CommentDto commentDto;

    @BeforeEach
    void setUp() {
        newItemRequest = new NewItemRequest("Test Item", "Test Description", true, null);
        updateItemRequest = new UpdateItemRequest("Updated Item", "Updated Description", false);
        commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setText("Test Comment");
        commentDto.setItemId(1L);
        commentDto.setAuthorId(1L);
        commentDto.setAuthorName("Test User");
        commentDto.setCreated(LocalDateTime.now());
    }

    @Test
    void getItemsById_success() throws Exception {
        when(itemClient.getItemsByUserId(anyLong(), anyInt(), anyInt()))
                .thenReturn(ResponseEntity.ok(List.of()));

        mockMvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1L)
                        .param("from", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getItemById_success() throws Exception {
        when(itemClient.getItemById(anyLong(), anyLong()))
                .thenReturn(ResponseEntity.ok(newItemRequest));

        mockMvc.perform(get("/items/1")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Item"));
    }

    @Test
    void searchItemByText_success() throws Exception {
        when(itemClient.searchItemByText(anyLong(), anyString(), anyInt(), anyInt()))
                .thenReturn(ResponseEntity.ok(List.of()));

        mockMvc.perform(get("/items/search")
                        .header("X-Sharer-User-Id", 1L)
                        .param("text", "test")
                        .param("from", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void createItem_success() throws Exception {
        when(itemClient.createItem(anyLong(), any(NewItemRequest.class)))
                .thenReturn(ResponseEntity.ok(newItemRequest));

        mockMvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItemRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Item"));
    }

    @Test
    void update_success() throws Exception {
        when(itemClient.updateItem(anyLong(), any(UpdateItemRequest.class), anyLong()))
                .thenReturn(ResponseEntity.ok(updateItemRequest));

        mockMvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateItemRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Item"));
    }

    @Test
    void comment_success() throws Exception {
        when(itemClient.comment(anyLong(), any(CommentDto.class), anyLong()))
                .thenReturn(ResponseEntity.ok(commentDto));

        mockMvc.perform(post("/items/1/comment")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Test Comment"));
    }
}
