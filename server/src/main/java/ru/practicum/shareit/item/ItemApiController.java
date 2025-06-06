package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemResponse;
import ru.practicum.shareit.item.dto.ItemWithBookingsResponse;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.ItemManager;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@Slf4j
public class ItemApiController {
    private final ItemManager itemManager;
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";

    @GetMapping
    public ResponseEntity<List<ItemWithBookingsResponse>> fetchUserItems(@RequestHeader(USER_ID_HEADER) Long userId) {
        log.info("Получение списка предметов пользователя с ID: {}", userId);
        return ResponseEntity.ok(itemManager.fetchUserItems(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> findById(@RequestHeader(USER_ID_HEADER) Long userId, @PathVariable Long id) {
        log.info("Получение предмета с ID: {} пользователем с ID: {}", id, userId);
        return ResponseEntity.ok(itemManager.findItemById(id, userId));
    }

    @PostMapping
    public ResponseEntity<ItemResponse> addItem(@RequestHeader(USER_ID_HEADER) Long userId,
                                                @RequestBody NewItemRequest request) {
        log.info("Добавление нового предмета для пользователя с ID: {}", userId);
        return ResponseEntity.ok(itemManager.addItem(request, userId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItem(@RequestHeader(USER_ID_HEADER) Long userId,
                                                   @PathVariable Long id,
                                                   @RequestBody UpdateItemRequest request) {
        log.info("Обновление предмета с ID: {} пользователем с ID: {}", id, userId);

        if (id == null) {
            log.warn("Получен запрос на обновление предмета с null ID от пользователя с ID: {}", userId);
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(itemManager.modifyItem(request, userId, id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemResponse>> searchItems(@RequestHeader(USER_ID_HEADER) Long userId,
                                                          @RequestParam String text) {
        log.info("Поиск предметов по запросу: {} для пользователя с ID: {}", text, userId);
        return ResponseEntity.ok(itemManager.searchItems(text, userId));
    }

    @DeleteMapping
    public ResponseEntity<Void> removeItem(@RequestHeader(USER_ID_HEADER) Long userId,
                                           @RequestParam Long id) {
        log.info("Удаление предмета с ID: {} пользователем с ID: {}", id, userId);
        itemManager.removeItem(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<CommentDto> addComment(@RequestHeader(USER_ID_HEADER) Long userId,
                                                 @PathVariable Long itemId,
                                                 @RequestBody CommentDto commentDto) {
        log.info("Добавление комментария к предмету с ID: {} пользователем с ID: {}", itemId, userId);
        return ResponseEntity.ok(itemManager.addComment(commentDto, userId, itemId));
    }
}