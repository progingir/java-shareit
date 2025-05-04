//package ru.practicum.shareit;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.practicum.shareit.booking.BookingRepository;
//import ru.practicum.shareit.exception.UserNotFoundException;
//import ru.practicum.shareit.item.dto.*;
//import ru.practicum.shareit.item.model.*;
//import ru.practicum.shareit.user.model.User;
//import ru.practicum.shareit.user.model.UserRepository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ItemManagerImplTest {
//
//    @Mock
//    private ItemRepository itemRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private ItemTransformer transformer;
//
//    @Mock
//    private BookingRepository bookingRepository;
//
//    @Mock
//    private CommentRepository commentRepository;
//
//    @InjectMocks
//    private ItemManagerImpl itemManager;
//
//    private User user;
//    private Item item;
//    private NewItemRequest newItemRequest;
//    private UpdateItemRequest updateItemRequest;
//    private ItemResponse itemResponse;
//    private CommentDto commentDto;
//
//    @BeforeEach
//    void setUp() {
//        user = new User();
//        user.setId(1L);
//        user.setName("Test User");
//        user.setEmail("test@example.com");
//
//        item = new Item();
//        item.setId(1L);
//        item.setName("Test Item");
//        item.setDescription("Test Description");
//        item.setAvailable(true);
//        item.setOwner(user);
//
//        newItemRequest = new NewItemRequest("Test Item", "Test Description", true, null);
//        updateItemRequest = new UpdateItemRequest("Updated Item", "Updated Description", false);
//        itemResponse = new ItemResponse(1L, "Test Item", "Test Description", true, null, null, List.of());
//
//        commentDto = new CommentDto();
//        commentDto.setText("Test Comment");
//        commentDto.setItemId(1L);
//        commentDto.setAuthorId(1L);
//        commentDto.setAuthorName("Test User");
//        commentDto.setCreated(LocalDateTime.now());
//    }
//
//    @Test
//    void addItem_success() {
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(transformer.toItem(newItemRequest)).thenReturn(item);
//        when(itemRepository.save(item)).thenReturn(item);
//        when(transformer.toResponse(item, List.of(), 1L)).thenReturn(itemResponse);
//
//        ItemResponse result = itemManager.addItem(newItemRequest, 1L);
//
//        assertNotNull(result);
//        assertEquals(itemResponse, result);
//        verify(itemRepository).save(item);
//    }
//
//    @Test
//    void addItem_userNotFound_throwsUserNotFoundException() {
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () -> itemManager.addItem(newItemRequest, 1L));
//    }
//
//    @Test
//    void findItemById_success_owner() {
//        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
//        when(bookingRepository.findByBookerId(anyLong(), any())).thenReturn(List.of());
//        when(commentRepository.findByItemId(1L)).thenReturn(List.of());
//        when(transformer.toResponse(item, List.of(), 1L)).thenReturn(itemResponse);
//
//        ItemResponse result = itemManager.findItemById(1L, 1L);
//
//        assertNotNull(result);
//        assertEquals(itemResponse, result);
//    }
//
//    @Test
//    void modifyItem_success() {
//        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
//        when(transformer.applyUpdates(updateItemRequest, item)).thenReturn(item);
//        when(itemRepository.save(item)).thenReturn(item);
//        when(transformer.toResponse(item, List.of(), 1L)).thenReturn(itemResponse);
//
//        ItemResponse result = itemManager.modifyItem(updateItemRequest, 1L, 1L);
//
//        assertNotNull(result);
//        assertEquals(itemResponse, result);
//        verify(itemRepository).save(item);
//    }
//
//    @Test
//    void fetchUserItems_success() {
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(itemRepository.findByOwnerId(1L)).thenReturn(List.of(item));
//        when(bookingRepository.findByItemOwnerId(eq(1L), any())).thenReturn(List.of());
//        when(commentRepository.findByItemId(1L)).thenReturn(List.of());
//        ItemWithBookingsResponse response = new ItemWithBookingsResponse(1L, "Test Item", "Test Description", true);
//        when(transformer.toResponseWithBookingsAndComments(any(), any(), any(), any())).thenReturn(response);
//
//        List<ItemWithBookingsResponse> result = itemManager.fetchUserItems(1L);
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(response, result.get(0));
//    }
//
//    @Test
//    void addComment_noCompletedBooking_throwsRuntimeException() {
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
//        when(bookingRepository.findByBookerId(eq(1L), any())).thenReturn(List.of());
//
//        assertThrows(RuntimeException.class, () -> itemManager.addComment(commentDto, 1L, 1L));
//    }
//}