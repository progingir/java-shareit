package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.booking.BookingClient;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingShortDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingClient bookingClient;

    @InjectMocks
    private BookingController bookingController;

    private BookingShortDto bookingShortDto;

    @BeforeEach
    void setUp() {
        bookingShortDto = new BookingShortDto();
        bookingShortDto.setId(1L);
        bookingShortDto.setStart(LocalDateTime.now().plusDays(1));
        bookingShortDto.setEnd(LocalDateTime.now().plusDays(2));
        bookingShortDto.setItemId(1L);
        bookingShortDto.setBookerId(1L);
        bookingShortDto.setStatus(BookingStatus.WAITING);
    }

    @Test
    void getBookings() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(new Object());
        when(bookingClient.getBookings(anyLong(), anyString(), anyInt(), anyInt()))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = bookingController.getBookings(1L, "ALL", 0, 10);

        assertEquals(expectedResponse, response);
    }

    @Test
    void bookItem() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(new Object());
        when(bookingClient.bookItem(anyLong(), any(BookingShortDto.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = bookingController.bookItem(1L, bookingShortDto);

        assertEquals(expectedResponse, response);
    }

    @Test
    void getBooking() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(new Object());
        when(bookingClient.getBooking(anyLong(), anyLong()))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = bookingController.getBooking(1L, 1L);

        assertEquals(expectedResponse, response);
    }

    @Test
    void update() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(new Object());
        when(bookingClient.updateStatus(anyLong(), anyLong(), anyBoolean()))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = bookingController.update(1L, 1L, true);

        assertEquals(expectedResponse, response);
    }

    @Test
    void getAllBookingsByOwnerId() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(new Object());
        when(bookingClient.getBookingsByOwnerId(anyLong(), anyString(), anyInt(), anyInt()))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = bookingController.getAllBookingsByOwnerId(1L, "ALL", 0, 10);

        assertEquals(expectedResponse, response);
    }
}
