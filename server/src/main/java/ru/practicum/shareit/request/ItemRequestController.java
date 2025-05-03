package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService requestService;
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<ItemRequestDto> createRequest(@RequestHeader(USER_ID_HEADER) Long userId,
                                                        @RequestBody ItemRequestDto requestDto) {
        return ResponseEntity.ok(requestService.createRequest(requestDto, userId));
    }

    @GetMapping
    public ResponseEntity<List<ItemRequestDto>> getUserRequests(@RequestHeader(USER_ID_HEADER) Long userId,
                                                                @RequestParam(defaultValue = "0") int from,
                                                                @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(requestService.getUserRequests(userId, from, size));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemRequestDto>> getAllRequests(@RequestHeader(USER_ID_HEADER) Long userId,
                                                               @RequestParam(defaultValue = "0") int from,
                                                               @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(requestService.getAllRequests(userId, from, size));
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ItemRequestDto> getRequestById(@RequestHeader(USER_ID_HEADER) Long userId,
                                                         @PathVariable Long requestId) {
        return ResponseEntity.ok(requestService.getRequestById(requestId, userId));
    }
}
