package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommentTest {

    @Test
    void testComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Great item!");
        Item item = new Item();
        item.setId(1L);
        comment.setItem(item);
        User author = new User();
        author.setId(1L);
        comment.setAuthor(author);
        LocalDateTime now = LocalDateTime.now();
        comment.setCreated(now);

        assertEquals(1L, comment.getId());
        assertEquals("Great item!", comment.getText());
        assertEquals(item, comment.getItem());
        assertEquals(author, comment.getAuthor());
        assertEquals(now, comment.getCreated());
    }

    @Test
    void testCommentWithNulls() {
        Comment comment = new Comment();
        assertNull(comment.getId());
        assertNull(comment.getText());
        assertNull(comment.getItem());
        assertNull(comment.getAuthor());
        assertNull(comment.getCreated());
    }
}