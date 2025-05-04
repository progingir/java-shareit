package ru.practicum.shareit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.ItemApiController;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.ItemManager;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemApiController.class)
class ItemApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemManager itemManager;

    @Autowired
    private ObjectMapper objectMapper;

    private ItemResponse itemResponse;
    private NewItemRequest newItemRequest;

    @BeforeEach
    void setUp() {
        itemResponse = new ItemResponse(1L, "Test Item", "Description", true, null, null, List.of());
        newItemRequest = new NewItemRequest("Test Item", "Description", true, null);
    }

    @Test
    void fetchUserItems_success() throws Exception {
        when(itemManager.fetchUserItems(1L)).thenReturn(List.of(new ItemWithBookingsResponse(1L, "Test Item", "Description", true)));

        mockMvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void findById_success() throws Exception {
        when(itemManager.findItemById(eq(1L), eq(1L))).thenReturn(itemResponse);

        mockMvc.perform(get("/items/1")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void addItem_success() throws Exception {
        when(itemManager.addItem(any(NewItemRequest.class), eq(1L))).thenReturn(itemResponse);

        mockMvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItemRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void searchItems_success() throws Exception {
        when(itemManager.searchItems(eq("test"), eq(1L))).thenReturn(List.of(itemResponse));

        mockMvc.perform(get("/items/search")
                        .header("X-Sharer-User-Id", 1L)
                        .param("text", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}
