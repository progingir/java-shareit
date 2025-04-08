package ru.practicum.shareit.item.model;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ItemStorageImpl implements ItemStorage {
    private final Map<Long, Item> storage = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Long add(Item item) {
        Long newId = idGenerator.getAndIncrement();
        item.setId(newId);
        storage.put(newId, item);
        return newId;
    }

    @Override
    public List<Item> fetchAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Item> fetchByOwnerId(long ownerId) {
        return storage.values().stream()
                .filter(item -> item.getOwner().getId() == ownerId)
                .toList();
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void modify(Item updatedItem) {
        Long itemId = updatedItem.getId();
        if (storage.containsKey(itemId)) {
            Item existing = storage.get(itemId);
            existing.setName(updatedItem.getName());
            existing.setDescription(updatedItem.getDescription());
            existing.setAvailable(updatedItem.isAvailable());
        }
    }

    @Override
    public void remove(Long id) {
        storage.remove(id);
    }

    @Override
    public List<Item> findByText(String query) {
        if (query == null || query.isBlank()) {
            return List.of();
        }
        String lowerCaseQuery = query.toLowerCase();
        return storage.values().stream()
                .filter(i -> i.isAvailable() &&
                        ((i.getName() != null && i.getName().toLowerCase().contains(lowerCaseQuery)) ||
                                (i.getDescription() != null && i.getDescription().toLowerCase().contains(lowerCaseQuery))))
                .toList();
    }
}