package ru.practicum.shareit.request;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemRequestMapper {

    public ItemRequestDto toDto(ItemRequest request, List<Item> items) {
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(request.getId());
        String description = request.getDescription() != null ? request.getDescription() : "";
        dto.setDescription(description);
        dto.setRequestorId(request.getRequestor().getId());
        ItemRequestDto.Requestor requestor = new ItemRequestDto.Requestor();
        requestor.setId(request.getRequestor().getId());
        requestor.setName(request.getRequestor().getName());
        dto.setRequestor(requestor);
        dto.setCreated(request.getCreated());
        dto.setItems(items.stream().map(this::toItemDto).collect(Collectors.toList()));
        return dto;
    }

    public ItemRequest toEntity(ItemRequestDto dto) {
        ItemRequest request = new ItemRequest();
        request.setDescription(dto.getDescription() != null ? dto.getDescription() : "");
        return request;
    }

    private ItemRequestDto.ItemDto toItemDto(Item item) {
        ItemRequestDto.ItemDto itemDto = new ItemRequestDto.ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setOwnerId(item.getOwner().getId());
        return itemDto;
    }
}