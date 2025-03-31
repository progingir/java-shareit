package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemStorageImpl;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ItemStorageImplTest {

    private ItemStorageImpl itemStorage;
    private User testUser;
    private Item testItem;

    @BeforeEach
    void setUp() {
        itemStorage = new ItemStorageImpl();
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");

        testItem = new Item();
        testItem.setName("Test Item");
        testItem.setDescription("Test Description");
        testItem.setOwner(testUser);
        testItem.setAvailable(true);
    }

    @Test
    void shouldAddAndFindItemById() {
        Long itemId = itemStorage.add(testItem);

        assertTrue(itemStorage.findById(itemId).isPresent());
        assertEquals(testItem, itemStorage.findById(itemId).get());
        assertEquals(itemId, testItem.getId());
    }

    @Test
    void shouldReturnItemsByOwnerId() {
        itemStorage.add(testItem);

        List<Item> items = itemStorage.fetchByOwnerId(1L);

        assertEquals(1, items.size());
        assertEquals(testItem, items.get(0));
    }

    @Test
    void shouldModifyItemSuccessfully() {
        Long itemId = itemStorage.add(testItem);
        Item updatedItem = new Item();
        updatedItem.setId(itemId);
        updatedItem.setName("Updated Item");
        updatedItem.setDescription("Updated Description");
        updatedItem.setAvailable(false);
        updatedItem.setOwner(testUser);

        itemStorage.modify(updatedItem);

        Item result = itemStorage.findById(itemId).get();
        assertEquals("Updated Item", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertFalse(result.isAvailable());
    }

    @Test
    void shouldReturnItemsWhenSearchingByText() {
        itemStorage.add(testItem);

        List<Item> result = itemStorage.findByText("test");

        assertEquals(1, result.size());
        assertEquals(testItem, result.get(0));
    }

    @Test
    void shouldRemoveItemSuccessfully() {
        Long itemId = itemStorage.add(testItem);

        itemStorage.remove(itemId);

        assertFalse(itemStorage.findById(itemId).isPresent());
    }
}