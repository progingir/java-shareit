package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.ForbiddenAccessException;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public BookingDto createBooking(BookingDto bookingDto, Long userId) {
        User booker = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Item item = itemRepository.findById(bookingDto.getItem())
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));
        if (!item.isAvailable()) {
            throw new IllegalArgumentException("Item is not available");
        }
        if (item.getOwner().getId().equals(userId)) {
            throw new ForbiddenAccessException("Owner cannot book their own item");
        }

        Booking booking = new Booking();
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        booking.setItem(item);
        booking.setBooker(booker);
        booking.setStatus(BookingStatus.WAITING);

        booking = bookingRepository.save(booking);
        return toDto(booking);
    }

    @Override
    public BookingDto updateBooking(Long bookingId, Long userId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new ForbiddenAccessException("Only owner can approve/reject booking");
        }
        if (booking.getStatus() != BookingStatus.WAITING) {
            throw new IllegalArgumentException("Booking status cannot be changed");
        }

        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        booking = bookingRepository.save(booking);
        return toDto(booking);
    }

    @Override
    public BookingDto getBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        if (!booking.getBooker().getId().equals(userId) && !booking.getItem().getOwner().getId().equals(userId)) {
            throw new ForbiddenAccessException("Access denied");
        }
        return toDto(booking);
    }

    @Override
    public List<BookingDto> getUserBookings(Long userId, String state) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Sort sort = Sort.by(Sort.Direction.DESC, "start");
        List<Booking> bookings;
        LocalDateTime now = LocalDateTime.now();

        switch (state.toUpperCase()) {
            case "CURRENT":
                bookings = bookingRepository.findByBookerIdAndStartBeforeAndEndAfter(userId, now, now, sort);
                break;
            case "PAST":
                bookings = bookingRepository.findByBookerIdAndEndBefore(userId, now, sort);
                break;
            case "FUTURE":
                bookings = bookingRepository.findByBookerIdAndStartAfter(userId, now, sort);
                break;
            case "WAITING":
                bookings = bookingRepository.findByBookerId(userId, sort)
                        .stream().filter(b -> b.getStatus() == BookingStatus.WAITING)
                        .collect(Collectors.toList());
                break;
            case "REJECTED":
                bookings = bookingRepository.findByBookerId(userId, sort)
                        .stream().filter(b -> b.getStatus() == BookingStatus.REJECTED)
                        .collect(Collectors.toList());
                break;
            default: // ALL
                bookings = bookingRepository.findByBookerId(userId, sort);
        }
        return bookings.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getOwnerBookings(Long userId, String state) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Sort sort = Sort.by(Sort.Direction.DESC, "start");
        List<Booking> bookings;
        LocalDateTime now = LocalDateTime.now();

        switch (state.toUpperCase()) {
            case "CURRENT":
                bookings = bookingRepository.findByItemOwnerId(userId, sort)
                        .stream().filter(b -> b.getStart().isBefore(now) && b.getEnd().isAfter(now))
                        .collect(Collectors.toList());
                break;
            case "PAST":
                bookings = bookingRepository.findByItemOwnerId(userId, sort)
                        .stream().filter(b -> b.getEnd().isBefore(now))
                        .collect(Collectors.toList());
                break;
            case "FUTURE":
                bookings = bookingRepository.findByItemOwnerId(userId, sort)
                        .stream().filter(b -> b.getStart().isAfter(now))
                        .collect(Collectors.toList());
                break;
            case "WAITING":
                bookings = bookingRepository.findByItemOwnerId(userId, sort)
                        .stream().filter(b -> b.getStatus() == BookingStatus.WAITING)
                        .collect(Collectors.toList());
                break;
            case "REJECTED":
                bookings = bookingRepository.findByItemOwnerId(userId, sort)
                        .stream().filter(b -> b.getStatus() == BookingStatus.REJECTED)
                        .collect(Collectors.toList());
                break;
            default: // ALL
                bookings = bookingRepository.findByItemOwnerId(userId, sort);
        }
        return bookings.stream().map(this::toDto).collect(Collectors.toList());
    }

    private BookingDto toDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setStart(booking.getStart());
        dto.setEnd(booking.getEnd());
        dto.setItem(booking.getItem().getId());
        dto.setBooker(booking.getBooker().getId());
        dto.setStatus(booking.getStatus().name());
        return dto;
    }
}