package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.CommentDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommentDtoTest {

    @Test
    void testCommentDto() {
        CommentDto dto = new CommentDto();
        dto.setId(1L);
        dto.setText("Great item!");
        dto.setItemId(1L);
        dto.setAuthorId(1L);
        dto.setAuthorName("John");
        dto.setCreated(LocalDateTime.now());

        assertEquals(1L, dto.getId());
        assertEquals("Great item!", dto.getText());
        assertEquals(1L, dto.getItemId());
        assertEquals(1L, dto.getAuthorId());
        assertEquals("John", dto.getAuthorName());
        assertEquals(LocalDateTime.now().withNano(0), dto.getCreated().withNano(0)); // Сравнение без наносекунд
    }

    @Test
    void testCommentDtoWithNulls() {
        CommentDto dto = new CommentDto();
        assertNull(dto.getId());
        assertNull(dto.getText());
        assertNull(dto.getItemId());
        assertNull(dto.getAuthorId());
        assertNull(dto.getAuthorName());
        assertNull(dto.getCreated());
    }
}
