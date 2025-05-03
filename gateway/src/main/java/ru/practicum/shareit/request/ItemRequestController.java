package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemRequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> createItemRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                    @Valid @RequestBody ItemRequestDto itemRequestDto) {
        log.info("Request to create new itemRequest received: {}", itemRequestDto);
        return requestClient.createRequest(userId, itemRequestDto);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequestByRequestId(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                            @PathVariable long requestId) {
        log.info("Request to get itemRequest with ID {} received.", requestId);
        return requestClient.getItemRequestById(userId, requestId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItemRequestsByUserId(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                             @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                             @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Request to get all itemRequests of the user with ID {} received.", userId);
        return requestClient.getItemRequestsByUserId(userId, from, size);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllItemRequestsByOthers(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                             @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                             @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Request to get all itemRequests of others");
        return requestClient.getItemRequestsByOthers(userId, from, size);
    }
}