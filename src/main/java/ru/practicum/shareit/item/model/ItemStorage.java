package ru.practicum.shareit.item.model;

import java.util.List;
import java.util.Optional;

public interface ItemStorage {
    Long add(Item item);

    List<Item> fetchAll();

    List<Item> fetchByOwnerId(long ownerId);

    Optional<Item> findById(Long id);

    void modify(Item item);

    void remove(Long id);

    List<Item> findByText(String query);
}