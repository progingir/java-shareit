package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.booking.dto.BookingShortDto;

import java.util.HashMap;
import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build());
    }

    public ResponseEntity<Object> getBookings(long userId, String state, Integer from, Integer size) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("state", state);
        parameters.put("from", from);
        parameters.put("size", size);
        return get("?state={state}&from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> bookItem(long userId, BookingShortDto requestDto) {
        return post("", userId, requestDto);
    }

    public ResponseEntity<Object> getBooking(long userId, Long bookingId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> updateStatus(long userId, Long bookingId, boolean approved) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("bookingId", bookingId);
        parameters.put("approved", approved);
        return patch("/{bookingId}?approved={approved}", userId, parameters, null);
    }

    public ResponseEntity<Object> getBookingsByOwnerId(long userId, String state, Integer from, Integer size) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("state", state);
        parameters.put("from", from);
        parameters.put("size", size);
        return get("/owner?state={state}&from={from}&size={size}", userId, parameters);
    }
}