package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.model.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemRequestMapper mapper;

    @Override
    public ItemRequestDto createRequest(ItemRequestDto requestDto, Long userId) {
        User requestor = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + userId + " не найден"));
        ItemRequest request = mapper.toEntity(requestDto);
        request.setRequestor(requestor);
        request.setCreated(LocalDateTime.now());
        request = requestRepository.save(request);
        return mapper.toDto(request, List.of());
    }

    @Override
    public List<ItemRequestDto> getUserRequests(Long userId, int from, int size) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + userId + " не найден"));
        PageRequest page = PageRequest.of(from / size, size, Sort.by("created").descending());
        List<ItemRequest> requests = requestRepository.findByRequestorId(userId, page);
        return requests.stream()
                .map(req -> mapper.toDto(req, itemRepository.findByItemRequest_Id(req.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemRequestDto> getAllRequests(Long userId, int from, int size) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + userId + " не найден"));
        PageRequest page = PageRequest.of(from / size, size, Sort.by("created").descending());
        List<ItemRequest> requests = requestRepository.findByRequestorIdNot(userId, page);
        return requests.stream()
                .map(req -> mapper.toDto(req, itemRepository.findByItemRequest_Id(req.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto getRequestById(Long requestId, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + userId + " не найден"));
        ItemRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Запрос с ID " + requestId + " не найден"));
        return mapper.toDto(request, itemRepository.findByItemRequest_Id(requestId));
    }
}