package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingShortDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemTransformerImpl implements ItemTransformer {
    private final BookingRepository bookingRepository;
    private final ItemRequestRepository requestRepository;

    @Override
    public ItemResponse toResponse(Item item) {
        return toResponse(item, List.of(), null);
    }

    @Override
    public ItemResponse toResponse(Item item, List<CommentDto> comments, Long userId) {
        BookingShortDto lastBooking = null;
        BookingShortDto nextBooking = null;

        if (userId != null && item.getOwner() != null && userId.equals(item.getOwner().getId())) {
            LocalDateTime now = LocalDateTime.now();
            Sort sort = Sort.by(Sort.Direction.DESC, "start");

            lastBooking = findLastBooking(item.getId(), item.getOwner().getId(), now, sort);
            nextBooking = findNextBooking(item.getId(), item.getOwner().getId(), now, sort);
        }

        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                lastBooking,
                nextBooking,
                comments
        );
    }

    @Override
    public ItemWithBookingsResponse toResponseWithBookingsAndComments(
            Item item,
            BookingShortDto lastBooking,
            BookingShortDto nextBooking,
            List<CommentDto> comments
    ) {
        ItemWithBookingsResponse response = new ItemWithBookingsResponse(
                item.getId(), item.getName(), item.getDescription(), item.isAvailable()
        );
        response.setLastBooking(lastBooking);
        response.setNextBooking(nextBooking);
        response.setComments(comments);
        return response;
    }

    @Override
    public Item toItem(NewItemRequest request) {
        Item item = new Item();
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setAvailable(request.getAvailable() != null && request.getAvailable());
        if (request.getRequestId() != null) {
            ItemRequest itemRequest = requestRepository.findById(request.getRequestId())
                    .orElseThrow(() -> new IllegalArgumentException("Запрос с ID " + request.getRequestId() + " не найден"));
            item.setItemRequest(itemRequest);
        }
        return item;
    }

    @Override
    public Item applyUpdates(UpdateItemRequest updates, Item item) {
        if (updates.getName() != null) item.setName(updates.getName());
        if (updates.getDescription() != null) item.setDescription(updates.getDescription());
        if (updates.getAvailable() != null) item.setAvailable(updates.getAvailable());
        return item;
    }

    private BookingShortDto findLastBooking(Long itemId, Long ownerId, LocalDateTime now, Sort sort) {
        return bookingRepository.findByItemOwnerId(ownerId, sort)
                .stream()
                .filter(b -> b.getItem().getId().equals(itemId) && b.getEnd().isBefore(now))
                .max(Comparator.comparing(Booking::getEnd))
                .map(this::toBookingShortDto)
                .orElse(null);
    }

    private BookingShortDto findNextBooking(Long itemId, Long ownerId, LocalDateTime now, Sort sort) {
        return bookingRepository.findByItemOwnerId(ownerId, sort)
                .stream()
                .filter(b -> b.getItem().getId().equals(itemId) && b.getStart().isAfter(now))
                .min(Comparator.comparing(Booking::getStart))
                .map(this::toBookingShortDto)
                .orElse(null);
    }

    private BookingShortDto toBookingShortDto(Booking booking) {
        BookingShortDto dto = new BookingShortDto();
        dto.setId(booking.getId());
        dto.setBookerId(booking.getBooker().getId());
        dto.setStart(booking.getStart());
        dto.setEnd(booking.getEnd());
        return dto;
    }
}