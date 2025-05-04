package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestMapper;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.ItemRequestServiceImpl;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceImplTest {

    @Mock
    private ItemRequestRepository requestRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemRequestMapper mapper;

    @InjectMocks
    private ItemRequestServiceImpl requestService;

    @Test
    void createRequest() {
        User user = new User();
        user.setId(1L);
        ItemRequest request = new ItemRequest();
        request.setId(1L);
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(1L);
        Item item = new Item();
        item.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mapper.toEntity(dto)).thenReturn(request);
        when(requestRepository.save(any(ItemRequest.class))).thenReturn(request);
        when(itemRepository.findByItemRequest_Id(1L)).thenReturn(Collections.singletonList(item));
        when(mapper.toDto(request, Collections.singletonList(item))).thenReturn(dto);

        ItemRequestDto result = requestService.createRequest(dto, 1L);
        assertEquals(dto, result);
    }

    @Test
    void getUserRequests() {
        User user = new User();
        user.setId(1L);
        ItemRequest request = new ItemRequest();
        request.setId(1L);
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(requestRepository.findByRequestorId(anyLong(), any(PageRequest.class))).thenReturn(Collections.singletonList(request));
        when(itemRepository.findByItemRequest_Id(1L)).thenReturn(Collections.emptyList());
        when(mapper.toDto(request, Collections.emptyList())).thenReturn(dto);

        List<ItemRequestDto> result = requestService.getUserRequests(1L, 0, 10);
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void getAllRequests() {
        User user = new User();
        user.setId(1L);
        ItemRequest request = new ItemRequest();
        request.setId(1L);
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(requestRepository.findByRequestorIdNot(anyLong(), any(PageRequest.class))).thenReturn(Collections.singletonList(request));
        when(itemRepository.findByItemRequest_Id(1L)).thenReturn(Collections.emptyList());
        when(mapper.toDto(request, Collections.emptyList())).thenReturn(dto);

        List<ItemRequestDto> result = requestService.getAllRequests(1L, 0, 10);
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void getRequestById() {
        User user = new User();
        user.setId(1L);
        ItemRequest request = new ItemRequest();
        request.setId(1L);
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(requestRepository.findById(1L)).thenReturn(Optional.of(request));
        when(itemRepository.findByItemRequest_Id(1L)).thenReturn(Collections.emptyList());
        when(mapper.toDto(request, Collections.emptyList())).thenReturn(dto);

        ItemRequestDto result = requestService.getRequestById(1L, 1L);
        assertEquals(dto, result);
    }

    @Test
    void getRequestByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(requestRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> requestService.getRequestById(1L, 1L));
    }
}
