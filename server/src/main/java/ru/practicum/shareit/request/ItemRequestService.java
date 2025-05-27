package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {
    ItemRequestDto createRequest(ItemRequestDto requestDto, Long userId);

    List<ItemRequestDto> getUserRequests(Long userId, int from, int size);

    List<ItemRequestDto> getAllRequests(Long userId, int from, int size);

    ItemRequestDto getRequestById(Long requestId, Long userId);
}
