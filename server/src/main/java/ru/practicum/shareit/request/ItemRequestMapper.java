package ru.practicum.shareit.request;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.OwnerDto;
import ru.practicum.shareit.request.dto.RequesterDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemRequestMapper {

    public ItemRequestDto toDto(ItemRequest request, List<Item> items) {
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(request.getId());
        dto.setDescription(request.getDescription() != null ? request.getDescription() : "");

        RequesterDto requester = new RequesterDto();
        User requestor = request.getRequestor();
        if (requestor != null) {
            requester.setId(requestor.getId());
            requester.setEmail(requestor.getEmail() != null ? requestor.getEmail() : "");
            requester.setName(requestor.getName() != null ? requestor.getName() : "");
        } else {
            requester.setName("");
            requester.setEmail("");
        }
        dto.setRequester(requester);

        dto.setCreated(request.getCreated());

        dto.setItems(items != null ? items.stream()
                .map(this::toItemDto)
                .collect(Collectors.toList()) : List.of());

        return dto;
    }

    public ItemRequest toEntity(ItemRequestDto dto) {
        ItemRequest request = new ItemRequest();
        request.setDescription(dto.getDescription() != null ? dto.getDescription() : "");
        return request;
    }

    private ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName() != null ? item.getName() : "");
        itemDto.setDescription(item.getDescription() != null ? item.getDescription() : "");
        itemDto.setAvailable(item.isAvailable());

        OwnerDto owner = new OwnerDto();
        User ownerUser = item.getOwner();
        if (ownerUser != null) {
            owner.setId(ownerUser.getId());
            owner.setEmail(ownerUser.getEmail() != null ? ownerUser.getEmail() : "");
            owner.setName(ownerUser.getName() != null ? ownerUser.getName() : "");
        } else {
            owner.setEmail("");
            owner.setName("");
        }
        itemDto.setOwner(owner);

        itemDto.setRequestId(item.getItemRequest() != null ? item.getItemRequest().getId() : null);

        return itemDto;
    }
}