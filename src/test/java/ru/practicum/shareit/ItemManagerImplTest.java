package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemManagerImpl;
import ru.practicum.shareit.item.model.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserStorage;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemManagerImplTest {

    @Mock
    private ItemStorage itemStorage;

    @Mock
    private UserStorage userStorage;

    @Mock
    private ItemTransformer transformer;

    @InjectMocks
    private ItemManagerImpl itemManager;

    private User testUser;
    private Item testItem;
    private NewItemRequest newItemRequest;
    private ItemResponse itemResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");

        testItem = new Item();
        testItem.setId(1L);
        testItem.setName("Test Item");
        testItem.setDescription("Test Description");
        testItem.setOwner(testUser);
        testItem.setAvailable(true);

        newItemRequest = new NewItemRequest("Test Item", "Test Description", true);
        itemResponse = new ItemResponse(1L, "Test Item", "Test Description", true);
    }

    @Test
    void shouldReturnAllItemsWhenFetchingAll() {
        when(itemStorage.fetchAll()).thenReturn(List.of(testItem));
        when(transformer.toResponse(testItem)).thenReturn(itemResponse);

        List<ItemResponse> result = itemManager.fetchAllItems();

        assertEquals(1, result.size());
        assertEquals(itemResponse, result.get(0));
        verify(itemStorage).fetchAll();
        verify(transformer).toResponse(testItem);
    }

    @Test
    void shouldAddItemWhenUserExists() {
        when(userStorage.findById(1L)).thenReturn(Optional.of(testUser));
        when(transformer.toItem(newItemRequest)).thenReturn(testItem);
        when(itemStorage.add(testItem)).thenReturn(1L);
        when(transformer.toResponse(testItem)).thenReturn(itemResponse);

        ItemResponse result = itemManager.addItem(newItemRequest, 1L);

        assertEquals(itemResponse, result);
        assertEquals(testUser, testItem.getOwner());
        verify(itemStorage).add(testItem);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenAddingItemWithNonExistentUser() {
        when(userStorage.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> itemManager.addItem(newItemRequest, 1L));
        verify(itemStorage, never()).add(any());
    }

    @Test
    void shouldModifyItemWhenValidRequest() {
        UpdateItemRequest updateRequest = new UpdateItemRequest("New Name", null, null);
        Item updatedItem = new Item();
        updatedItem.setId(1L);
        updatedItem.setName("New Name");
        updatedItem.setOwner(testUser);

        when(itemStorage.findById(1L)).thenReturn(Optional.of(testItem));
        when(transformer.applyUpdates(updateRequest, testItem)).thenReturn(updatedItem);
        when(transformer.toResponse(updatedItem)).thenReturn(new ItemResponse(1L, "New Name", "Test Description", true));

        ItemResponse result = itemManager.modifyItem(updateRequest, 1L, 1L);

        assertEquals("New Name", result.getName());
        verify(itemStorage).modify(updatedItem);
    }

    @Test
    void shouldReturnEmptyListWhenSearchingWithEmptyQuery() {
        when(userStorage.findById(1L)).thenReturn(Optional.of(testUser));

        List<ItemResponse> result = itemManager.searchItems("", 1L);

        assertTrue(result.isEmpty());
        verify(itemStorage, never()).findByText(any());
    }

    @Test
    void shouldReturnItemsWhenSearchingWithValidQuery() {
        when(userStorage.findById(1L)).thenReturn(Optional.of(testUser));
        when(itemStorage.findByText("test")).thenReturn(List.of(testItem));
        when(transformer.toResponse(testItem)).thenReturn(itemResponse);

        List<ItemResponse> result = itemManager.searchItems("test", 1L);

        assertEquals(1, result.size());
        assertEquals(itemResponse, result.get(0));
        verify(itemStorage).findByText("test");
    }
}