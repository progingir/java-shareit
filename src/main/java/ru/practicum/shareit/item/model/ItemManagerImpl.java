package ru.practicum.shareit.item.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.exception.ForbiddenAccessException;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemResponse;
import ru.practicum.shareit.item.dto.ItemTransformer;
import ru.practicum.shareit.item.dto.ItemWithBookingsResponse;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemManagerImpl implements ItemManager {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemTransformer transformer;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<ItemResponse> fetchAllItems() {
        List<ItemResponse> items = itemRepository.findAll().stream()
                .map(transformer::toResponse)
                .toList();
        log.debug("Получено {} предметов", items.size());
        return items;
    }

    @Override
    public ItemResponse addItem(NewItemRequest request, Long userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Пользователь с ID {} не найден", userId);
                    return new UserNotFoundException("Пользователь с ID " + userId + " не найден");
                });
        Item item = transformer.toItem(request);
        item.setOwner(owner);
        Item savedItem = itemRepository.save(item);
        log.debug("Добавлен новый предмет: {}", savedItem);
        return transformer.toResponse(savedItem);
    }

    @Override
    public ItemResponse findItemById(Long id, Long userId) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Предмет с ID {} не найден", id);
                    return new ItemNotFoundException("Предмет с ID " + id + " не найден");
                });
        boolean isOwner = item.getOwner().getId().equals(userId);
        boolean hasBooking = bookingRepository.findByBookerId(userId, Sort.by(Sort.Direction.DESC, "start"))
                .stream()
                .anyMatch(b -> b.getItem().getId().equals(id));
        if (!isOwner && !hasBooking) {
            log.warn("Пользователь с ID {} не имеет доступа к предмету с ID {}", userId, id);
            throw new IllegalArgumentException("Пользователь не имеет доступа к предмету");
        }
        List<CommentDto> comments = commentRepository.findByItemId(id).stream()
                .map(this::toCommentDto)
                .collect(Collectors.toList());
        return transformer.toResponse(item, comments);
    }

    @Override
    public ItemResponse modifyItem(UpdateItemRequest request, Long userId, Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> {
                    log.warn("Предмет с ID {} не найден для обновления", itemId);
                    return new ItemNotFoundException("Предмет с ID " + itemId + " не найден");
                });
        if (!item.getOwner().getId().equals(userId)) {
            log.warn("Пользователь с ID {} не владеет предметом с ID {}", userId, itemId);
            throw new ForbiddenAccessException("Пользователь с ID " + userId + " не владеет предметом с ID " + itemId);
        }
        Item updatedItem = transformer.applyUpdates(request, item);
        updatedItem = itemRepository.save(updatedItem);
        log.debug("Обновлен предмет: {}", updatedItem);
        return transformer.toResponse(updatedItem);
    }

    @Override
    public List<ItemWithBookingsResponse> fetchUserItems(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Пользователь с ID {} не найден", userId);
                    return new UserNotFoundException("Пользователь с ID " + userId + " не найден");
                });
        List<Item> items = itemRepository.findByOwnerId(userId);
        LocalDateTime now = LocalDateTime.now();
        Sort sort = Sort.by(Sort.Direction.DESC, "start");

        return items.stream().map(item -> {
            List<Booking> bookings = bookingRepository.findByItemOwnerId(userId, sort);
            BookingShortDto lastBooking = bookings.stream()
                    .filter(b -> b.getItem().getId().equals(item.getId()) && b.getEnd().isBefore(now))
                    .max(Comparator.comparing(Booking::getEnd))
                    .map(this::toBookingShortDto)
                    .orElse(null);
            BookingShortDto nextBooking = bookings.stream()
                    .filter(b -> b.getItem().getId().equals(item.getId()) && b.getStart().isAfter(now))
                    .min(Comparator.comparing(Booking::getStart))
                    .map(this::toBookingShortDto)
                    .orElse(null);
            List<CommentDto> comments = commentRepository.findByItemId(item.getId()).stream()
                    .map(this::toCommentDto)
                    .collect(Collectors.toList());

            return transformer.toResponseWithBookingsAndComments(item, lastBooking, nextBooking, comments);
        }).collect(Collectors.toList());
    }

    @Override
    public void removeItem(Long id, Long userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Пользователь с ID {} не найден", userId);
                    return new UserNotFoundException("Пользователь с ID " + userId + " не найден");
                });
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Предмет с ID {} не найден для удаления", id);
                    return new ItemNotFoundException("Предмет с ID " + id + " не найден");
                });
        if (!item.getOwner().getId().equals(userId)) {
            log.warn("Пользователь с ID {} не владеет предметом с ID {}", userId, id);
            throw new ForbiddenAccessException("Пользователь с ID " + userId + " не владеет предметом с ID " + id);
        }
        itemRepository.deleteById(id);
        log.debug("Удален предмет с ID {} пользователем с ID {}", id, userId);
    }

    @Override
    public List<ItemResponse> searchItems(String query, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Пользователь с ID {} не найден", userId);
                    return new UserNotFoundException("Пользователь с ID " + userId + " не найден");
                });
        if (query == null || query.isBlank()) {
            log.debug("Search query is null or blank, returning empty list");
            return List.of();
        }

        List<ItemResponse> items = itemRepository.search(query).stream()
                .map(transformer::toResponse)
                .toList();
        log.debug("Найдено {} предметов по запросу: {}", items.size(), query);
        return items;
    }

    @Override
    public CommentDto addComment(CommentDto commentDto, Long userId, Long itemId) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + userId + " не найден"));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Предмет с ID " + itemId + " не найден"));

        LocalDateTime now = LocalDateTime.now();
        boolean hasBooking = bookingRepository.findByBookerId(userId, Sort.by(Sort.Direction.DESC, "start")).stream()
                .anyMatch(b -> b.getItem().getId().equals(itemId) && b.getEnd().isBefore(now));
        if (!hasBooking) {
            log.warn("Пользователь с ID {} не арендовал предмет с ID {} или бронирование не завершено", userId, itemId);
            throw new IllegalArgumentException("Комментарий может оставить только пользователь, завершивший бронирование");
        }

        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setItem(item);
        comment.setAuthor(author);
        comment.setCreated(now);

        Comment savedComment = commentRepository.save(comment);
        log.debug("Добавлен комментарий к предмету с ID {}: {}", itemId, savedComment);
        return toCommentDto(savedComment);
    }

    private CommentDto toCommentDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setItemId(comment.getItem().getId());
        dto.setAuthorId(comment.getAuthor().getId());
        dto.setAuthorName(comment.getAuthor().getName());
        dto.setCreated(comment.getCreated());
        return dto;
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