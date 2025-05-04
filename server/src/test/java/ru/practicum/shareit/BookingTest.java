package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BookingTest {

    private Booking booking;

    @BeforeEach
    void setUp() {
        booking = new Booking();
        booking.setId(1L);
        booking.setStart(LocalDateTime.now().plusDays(1));
        booking.setEnd(LocalDateTime.now().plusDays(2));
        booking.setItem(new Item());
        booking.setBooker(new User());
        booking.setStatus(BookingStatus.WAITING);
    }

    @Test
    void gettersAndSetters_workCorrectly() {
        assertThat(booking.getId()).isEqualTo(1L);
        assertThat(booking.getStatus()).isEqualTo(BookingStatus.WAITING);

        booking.setId(2L);
        booking.setStatus(BookingStatus.APPROVED);
        assertThat(booking.getId()).isEqualTo(2L);
        assertThat(booking.getStatus()).isEqualTo(BookingStatus.APPROVED);
    }

    @Test
    void equalsAndHashCode_sameObjectsAreEqual() {
        Booking other = new Booking();
        other.setId(1L);
        other.setStart(booking.getStart());
        other.setEnd(booking.getEnd());
        other.setItem(new Item());
        other.setBooker(new User());
        other.setStatus(BookingStatus.WAITING);

        assertThat(booking).isEqualTo(other);
        assertThat(booking.hashCode()).isEqualTo(other.hashCode());
    }
}
