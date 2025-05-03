package ru.practicum.shareit.request;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemRequestMapper {

    public ItemRequestDto toDto(ItemRequest request, List<Item> items) {
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(request.getId());
        dto.setDescription(request.getDescription());

        // Заполняем requester
        ItemRequestDto.Requester requester = new ItemRequestDto.Requester();
        User requestor = request.getRequestor();
        requester.setId(requestor.getId());
        requester.setEmail(requestor.getEmail());
        requester.setName(requestor.getName());
        dto.setRequester(requester);

        dto.setCreated(request.getCreated());

        // Преобразуем items
        dto.setItems(items.stream()
                .map(item -> {
                    ItemRequestDto.ItemDto itemDto = new ItemRequestDto.ItemDto();
                    itemDto.setId(item.getId());
                    itemDto.setName(item.getName());
                    itemDto.setDescription(item.getDescription());
                    itemDto.setAvailable(item.isAvailable());
                    itemDto.setRequestId(request.getId());
                    return itemDto;
                })
                .collect(Collectors.toList()));

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
        itemDto.setDescription(item.getDescription() != null ? item.getDescription() : "");
        itemDto.setAvailable(item.isAvailable());

        // Заполняем owner
        ItemRequestDto.Owner owner = new ItemRequestDto.Owner();
        User ownerUser = item.getOwner();
        owner.setId(ownerUser.getId());
        owner.setEmail(ownerUser.getEmail() != null ? ownerUser.getEmail() : "");
        owner.setName(ownerUser.getName() != null ? ownerUser.getName() : "");
        itemDto.setOwner(owner);

        // Устанавливаем requestId
        itemDto.setRequestId(item.getItemRequest() != null ? item.getItemRequest().getId() : null);

        return itemDto;
    }
}