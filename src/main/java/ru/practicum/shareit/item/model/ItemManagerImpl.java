package ru.practicum.shareit.item.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ForbiddenAccessException;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.dto.ItemResponse;
import ru.practicum.shareit.item.dto.ItemTransformer;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemManagerImpl implements ItemManager {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;
    private final ItemTransformer transformer;

    @Override
    public List<ItemResponse> fetchAllItems() {
        List<ItemResponse> items = itemStorage.fetchAll().stream()
                .map(transformer::toResponse)
                .toList();
        log.debug("Получено {} предметов", items.size());
        return items;
    }

    @Override
    public ItemResponse addItem(NewItemRequest request, Long userId) {
        User owner = userStorage.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Пользователь с ID {} не найден", userId);
                    return new UserNotFoundException("Пользователь с ID " + userId + " не найден");
                });
        Item item = transformer.toItem(request);
        item.setOwner(owner);
        Long itemId = itemStorage.add(item);
        item.setId(itemId);
        log.debug("Добавлен новый предмет: {}", item);
        return transformer.toResponse(item);
    }

    @Override
    public ItemResponse findItemById(Long id) {
        Item item = itemStorage.findById(id)
                .orElseThrow(() -> {
                    log.warn("Предмет с ID {} не найден", id);
                    return new ItemNotFoundException("Предмет с ID " + id + " не найден");
                });
        return transformer.toResponse(item);
    }

    @Override
    public ItemResponse modifyItem(UpdateItemRequest request, Long userId, Long itemId) {
        Item item = itemStorage.findById(itemId)
                .orElseThrow(() -> {
                    log.warn("Предмет с ID {} не найден для обновления", itemId);
                    return new ItemNotFoundException("Предмет с ID " + itemId + " не найден");
                });
        if (!item.getOwner().getId().equals(userId)) {
            log.warn("Пользователь с ID {} не владеет предметом с ID {}", userId, itemId);
            throw new ForbiddenAccessException("Пользователь с ID " + userId + " не владеет предметом с ID " + itemId);
        }
        Item updatedItem = transformer.applyUpdates(request, item);
        itemStorage.modify(updatedItem);
        log.debug("Обновлен предмет: {}", updatedItem);
        return transformer.toResponse(updatedItem);
    }

    @Override
    public List<ItemResponse> fetchUserItems(Long userId) {
        userStorage.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Пользователь с ID {} не найден", userId);
                    return new UserNotFoundException("Пользователь с ID " + userId + " не найден");
                });
        List<ItemResponse> items = itemStorage.fetchByOwnerId(userId).stream()
                .map(transformer::toResponse)
                .toList();
        log.debug("Получено {} предметов для пользователя с ID {}", items.size(), userId);
        return items;
    }

    @Override
    public void removeItem(Long id, Long userId) {
        User owner = userStorage.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Пользователь с ID {} не найден", userId);
                    return new UserNotFoundException("Пользователь с ID " + userId + " не найден");
                });
        Item item = itemStorage.findById(id)
                .orElseThrow(() -> {
                    log.warn("Предмет с ID {} не найден для удаления", id);
                    return new ItemNotFoundException("Предмет с ID " + id + " не найден");
                });
        if (!item.getOwner().getId().equals(userId)) {
            log.warn("Пользователь с ID {} не владеет предметом с ID {}", userId, id);
            throw new ForbiddenAccessException("Пользователь с ID " + userId + " не владеет предметом с ID " + id);
        }
        itemStorage.remove(id);
        log.debug("Удален предмет с ID {} пользователем с ID {}", id, userId);
    }

    @Override
    public List<ItemResponse> searchItems(String query, Long userId) {
        userStorage.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Пользователь с ID {} не найден", userId);
                    return new UserNotFoundException("Пользователь с ID " + userId + " не найден");
                });
        List<ItemResponse> items = itemStorage.findByText(query).stream()
                .map(transformer::toResponse)
                .toList();
        log.debug("Найдено {} предметов по запросу: {}", items.size(), query);
        return items;
    }
}