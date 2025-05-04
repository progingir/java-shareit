//package ru.practicum.shareit;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.practicum.shareit.booking.dto.BookerDto;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class BookerDtoTest {
//
//    private ObjectMapper objectMapper;
//    private BookerDto bookerDto;
//
//    @BeforeEach
//    void setUp() {
//        objectMapper = new ObjectMapper();
//        bookerDto = new BookerDto();
//        bookerDto.setId(1L);
//        bookerDto.setName("Test User");
//    }
//
//    @Test
//    void gettersAndSetters_workCorrectly() {
//        assertThat(bookerDto.getId()).isEqualTo(1L);
//        assertThat(bookerDto.getName()).isEqualTo("Test User");
//
//        bookerDto.setId(2L);
//        bookerDto.setName("New User");
//        assertThat(bookerDto.getId()).isEqualTo(2L);
//        assertThat(bookerDto.getName()).isEqualTo("New User");
//    }
//
//    @Test
//    void serializeAndDeserialize_success() throws Exception {
//        String json = objectMapper.writeValueAsString(bookerDto);
//        BookerDto deserialized = objectMapper.readValue(json, BookerDto.class);
//
//        assertThat(deserialized).isEqualTo(bookerDto);
//    }
//
//    @Test
//    void equalsAndHashCode_sameObjectsAreEqual() {
//        BookerDto other = new BookerDto();
//        other.setId(1L);
//        other.setName("Test User");
//
//        assertThat(bookerDto).isEqualTo(other);
//        assertThat(bookerDto.hashCode()).isEqualTo(other.hashCode());
//    }
//}