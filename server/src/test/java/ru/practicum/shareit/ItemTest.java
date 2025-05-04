package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    private Item item;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setId(1L);
        item.setName("Test Item");
        item.setDescription("Description");
        item.setOwner(new User());
        item.setAvailable(true);
    }

    @Test
    void gettersAndSetters_workCorrectly() {
        assertThat(item.getId()).isEqualTo(1L);
        assertThat(item.getName()).isEqualTo("Test Item");

        item.setId(2L);
        item.setName("New Item");
        assertThat(item.getId()).isEqualTo(2L);
        assertThat(item.getName()).isEqualTo("New Item");
    }

    @Test
    void equalsAndHashCode_sameObjectsAreEqual() {
        Item other = new Item();
        other.setId(1L);
        other.setName("Test Item");
        other.setDescription("Description");
        other.setOwner(new User());
        other.setAvailable(true);

        assertThat(item).isEqualTo(other);
        assertThat(item.hashCode()).isEqualTo(other.hashCode());
    }
}