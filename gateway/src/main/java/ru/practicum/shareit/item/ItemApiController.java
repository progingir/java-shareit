package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Validated
public class ItemApiController {
    private final ItemClient itemClient;

    @GetMapping
    public ResponseEntity<Object> getItemsById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                               @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                               @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        //log.info("Request to get all items of the user with ID {} received.", userId);
        return itemClient.getItemsByUserId(userId, from, size);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                              @PathVariable long itemId) {
        //log.info("Request to get item with ID {} received.", itemId);
        return itemClient.getItemById(userId, itemId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItemByText(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                   @RequestParam(name = "text") String text,
                                                   @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                   @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        //log.info("Request to get item containing text: {} received.", text);
        return itemClient.searchItemByText(userId, text, from, size);
    }

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                                             @RequestBody @Valid NewItemRequest itemDto) {
        //log.info("Request to create new item received: {}", itemDto);
        return itemClient.createItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @RequestBody @Valid UpdateItemRequest itemDto,
                                         @PathVariable long itemId) {
        //log.info("Request to update item received: {}", itemDto);
        return itemClient.updateItem(userId, itemDto, itemId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> comment(@RequestHeader("X-Sharer-User-Id") Long userId,
                                          @RequestBody @Valid CommentDto commentDto,
                                          @PathVariable long itemId) {
        //log.info("Request to comment item received: {}", commentDto);
        return itemClient.comment(userId, commentDto, itemId);
    }
}