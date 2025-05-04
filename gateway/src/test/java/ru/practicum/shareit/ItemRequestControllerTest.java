package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.request.RequestClient;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemRequestControllerTest {

    @Mock
    private RequestClient requestClient;

    @InjectMocks
    private ItemRequestController itemRequestController;

    private ItemRequestDto itemRequestDto;

    @BeforeEach
    void setUp() {
        itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(1L);
        itemRequestDto.setDescription("Test description");
    }

    @Test
    void createItemRequest() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(new Object());
        when(requestClient.createRequest(anyLong(), any(ItemRequestDto.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = itemRequestController.createItemRequest(1L, itemRequestDto);

        assertEquals(expectedResponse, response);
    }

    @Test
    void getItemRequestByRequestId() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(new Object());
        when(requestClient.getItemRequestById(anyLong(), anyLong()))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = itemRequestController.getItemRequestByRequestId(1L, 1L);

        assertEquals(expectedResponse, response);
    }

    @Test
    void getAllItemRequestsByUserId() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(new Object());
        when(requestClient.getItemRequestsByUserId(anyLong(), anyInt(), anyInt()))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = itemRequestController.getAllItemRequestsByUserId(1L, 0, 10);

        assertEquals(expectedResponse, response);
    }

    @Test
    void getAllItemRequestsByOthers() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(new Object());
        when(requestClient.getItemRequestsByOthers(anyLong(), anyInt(), anyInt()))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = itemRequestController.getAllItemRequestsByOthers(1L, 0, 10);

        assertEquals(expectedResponse, response);
    }
}
