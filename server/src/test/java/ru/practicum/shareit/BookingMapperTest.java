//package ru.practicum.shareit;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.practicum.shareit.booking.Booking;
//import ru.practicum.shareit.booking.BookingMapper;
//import ru.practicum.shareit.booking.BookingStatus;
//import ru.practicum.shareit.booking.dto.BookingDto;
//import ru.practicum.shareit.booking.dto.BookerDto;
//import ru.practicum.shareit.booking.dto.ItemDto;
//import ru.practicum.shareit.item.model.Item;
//import ru.practicum.shareit.user.model.User;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BookingMapperTest {
//
//    private BookingMapper bookingMapper;
//    private Booking booking;
//    private User booker;
//    private Item item;
//
//    @BeforeEach
//    void setUp() {
//        bookingMapper = new BookingMapper();
//
//        booker = new User();
//        booker.setId(1L);
//        booker.setName("Test User");
//        booker.setEmail("test@example.com");
//
//        item = new Item();
//        item.setId(1L);
//        item.setName("Test Item");
//
//        booking = new Booking();
//        booking.setId(1L);
//        booking.setStart(LocalDateTime.now());
//        booking.setEnd(LocalDateTime.now().plusDays(1));
//        booking.setItem(item);
//        booking.setBooker(booker);
//        booking.setStatus(BookingStatus.APPROVED);
//    }
//
//    @Test
//    void toDto_success() {
//        BookingDto result = bookingMapper.toDto(booking);
//
//        assertNotNull(result);
//        assertEquals(booking.getId(), result.getId());
//        assertEquals(booking.getStart(), result.getStart());
//        assertEquals(booking.getEnd(), result.getEnd());
//        assertEquals(booking.getStatus().name(), result.getStatus());
//
//        ItemDto itemDto = result.getItem();
//        assertNotNull(itemDto);
//        assertEquals(item.getId(), itemDto.getId());
//        assertEquals(item.getName(), itemDto.getName());
//
//        BookerDto bookerDto = result.getBooker();
//        assertNotNull(bookerDto);
//        assertEquals(booker.getId(), bookerDto.getId());
//        assertEquals(booker.getName(), bookerDto.getName());
//    }
//}
