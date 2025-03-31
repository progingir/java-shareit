package ru.practicum.shareit.item.model;

import ru.practicum.shareit.item.dto.ItemResponse;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;

import java.util.List;

public interface ItemManager {
    List<ItemResponse> fetchAllItems();

    ItemResponse addItem(NewItemRequest request, Long userId);

    ItemResponse findItemById(Long id);

    ItemResponse modifyItem(UpdateItemRequest request, Long userId, Long itemId);

    List<ItemResponse> fetchUserItems(Long userId);

    void removeItem(Long id, Long userId);

    List<ItemResponse> searchItems(String query, Long userId);
}