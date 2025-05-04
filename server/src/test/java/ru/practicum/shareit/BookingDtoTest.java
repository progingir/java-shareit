//package ru.practicum.shareit;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.practicum.shareit.booking.dto.BookerDto;
//import ru.practicum.shareit.booking.dto.BookingDto;
//import ru.practicum.shareit.booking.dto.ItemDto;
//
//import java.time.LocalDateTime;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class BookingDtoTest {
//
//    private ObjectMapper objectMapper;
//    private BookingDto bookingDto;
//
//    @BeforeEach
//    void setUp() {
//        objectMapper = new ObjectMapper();
//        bookingDto = new BookingDto();
//        bookingDto.setId(1L);
//        bookingDto.setStart(LocalDateTime.now().plusDays(1));
//        bookingDto.setEnd(LocalDateTime.now().plusDays(2));
//        bookingDto.setItem(new ItemDto());
//        bookingDto.getItem().setId(1L);
//        bookingDto.getItem().setName("Test Item");
//        bookingDto.setItemId(1L);
//        bookingDto.setBooker(new BookerDto());
//        bookingDto.getBooker().setId(2L);
//        bookingDto.getBooker().setName("Test User");
//        bookingDto.setBookerId(2L);
//        bookingDto.setStatus("WAITING");
//    }
//
//    @Test
//    void gettersAndSetters_workCorrectly() {
//        assertThat(bookingDto.getId()).isEqualTo(1L);
//        assertThat(bookingDto.getItemId()).isEqualTo(1L);
//        assertThat(bookingDto.getBookerId()).isEqualTo(2L);
//        assertThat(bookingDto.getStatus()).isEqualTo("WAITING");
//
//        bookingDto.setId(2L);
//        bookingDto.setItemId(2L);
//        bookingDto.setBookerId(3L);
//        bookingDto.setStatus("APPROVED");
//        assertThat(bookingDto.getId()).isEqualTo(2L);
//        assertThat(bookingDto.getItemId()).isEqualTo(2L);
//        assertThat(bookingDto.getBookerId()).isEqualTo(3L);
//        assertThat(bookingDto.getStatus()).isEqualTo("APPROVED");
//    }
//
//    @Test
//    void equalsAndHashCode_sameObjectsAreEqual() {
//        BookingDto other = new BookingDto();
//        other.setId(1L);
//        other.setStart(bookingDto.getStart());
//        other.setEnd(bookingDto.getEnd());
//        other.setItem(new ItemDto());
//        other.getItem().setId(1L);
//        other.getItem().setName("Test Item");
//        other.setItemId(1L);
//        other.setBooker(new BookerDto());
//        other.getBooker().setId(2L);
//        other.getBooker().setName("Test User");
//        other.setBookerId(2L);
//        other.setStatus("WAITING");
//
//        assertThat(bookingDto).isEqualTo(other);
//        assertThat(bookingDto.hashCode()).isEqualTo(other.hashCode());
//    }
//}