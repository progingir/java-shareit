//package ru.practicum.shareit;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.practicum.shareit.booking.Booking;
//import ru.practicum.shareit.booking.BookingRepository;
//import ru.practicum.shareit.booking.BookingShortDto;
//import ru.practicum.shareit.item.dto.*;
//import ru.practicum.shareit.item.model.Item;
//import ru.practicum.shareit.request.ItemRequestRepository;
//import ru.practicum.shareit.user.model.User;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class ItemTransformerImplTest {
//
//    @Mock
//    private BookingRepository bookingRepository;
//
//    @Mock
//    private ItemRequestRepository requestRepository;
//
//    @InjectMocks
//    private ItemTransformerImpl transformer;
//
//    private Item item;
//    private User owner;
//    private Booking booking;
//    private BookingShortDto bookingShortDto;
//
//    @BeforeEach
//    void setUp() {
//        owner = new User();
//        owner.setId(1L);
//        owner.setName("Owner");
//        owner.setEmail("owner@example.com");
//
//        item = new Item();
//        item.setId(1L);
//        item.setName("Test Item");
//        item.setDescription("Test Description");
//        item.setAvailable(true);
//        item.setOwner(owner);
//
//        booking = new Booking();
//        booking.setId(1L);
//        booking.setItem(item);
//        booking.setStart(LocalDateTime.now().minusDays(2));
//        booking.setEnd(LocalDateTime.now().minusDays(1));
//
//        bookingShortDto = new BookingShortDto();
//        bookingShortDto.setId(1L);
//        bookingShortDto.setBookerId(2L);
//        bookingShortDto.setStart(booking.getStart());
//        bookingShortDto.setEnd(booking.getEnd());
//    }
//
//    @Test
//    void toResponse_simple() {
//        ItemResponse result = transformer.toResponse(item);
//
//        assertNotNull(result);
//        assertEquals(item.getId(), result.getId());
//        assertEquals(item.getName(), result.getName());
//        assertEquals(item.getDescription(), result.getDescription());
//        assertEquals(item.isAvailable(), result.isAvailable());
//        assertNull(result.getLastBooking());
//        assertNull(result.getNextBooking());
//        assertTrue(result.getComments().isEmpty());
//    }
//
//    @Test
//    void toResponseWithBookingsAndComments_success() {
//        ItemWithBookingsResponse result = transformer.toResponseWithBookingsAndComments(
//                item, bookingShortDto, null, List.of(new CommentDto()));
//
//        assertNotNull(result);
//        assertEquals(item.getId(), result.getId());
//        assertEquals(item.getName(), result.getName());
//        assertEquals(item.getDescription(), result.getDescription());
//        assertEquals(item.isAvailable(), result.isAvailable());
//        assertEquals(bookingShortDto, result.getLastBooking());
//        assertNull(result.getNextBooking());
//        assertEquals(1, result.getComments().size());
//    }
//
//    @Test
//    void toItem_success() {
//        NewItemRequest request = new NewItemRequest("Test Item", "Test Description", true, null);
//        Item result = transformer.toItem(request);
//
//        assertNotNull(result);
//        assertEquals(request.getName(), result.getName());
//        assertEquals(request.getDescription(), result.getDescription());
//        assertEquals(request.getAvailable(), result.isAvailable());
//        assertNull(result.getItemRequest());
//    }
//
//    @Test
//    void applyUpdates_success() {
//        UpdateItemRequest request = new UpdateItemRequest("Updated Item", "Updated Description", false);
//        Item result = transformer.applyUpdates(request, item);
//
//        assertNotNull(result);
//        assertEquals(request.getName(), result.getName());
//        assertEquals(request.getDescription(), result.getDescription());
//        assertEquals(request.getAvailable(), result.isAvailable());
//    }
//}
