package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.client.BaseClient;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BaseClient baseClient;

    @BeforeEach
    void setUp() {
        baseClient = new BaseClient(restTemplate);
    }

    @Test
    void get_withoutParameters_success() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok("Success");
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = baseClient.get("/test");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody());
        verify(restTemplate).exchange(eq("/test"), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class));
    }

    @Test
    void get_withUserIdAndParameters_success() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok("Success");
        Map<String, Object> parameters = Map.of("key", "value");
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class), eq(parameters)))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = baseClient.get("/test", 1L, parameters);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody());
        verify(restTemplate).exchange(eq("/test"), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class), eq(parameters));
    }

    @Test
    void post_withBody_success() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok("Success");
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = baseClient.post("/test", "body");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody());
        verify(restTemplate).exchange(eq("/test"), eq(HttpMethod.POST), any(HttpEntity.class), eq(Object.class));
    }

    @Test
    void patch_withUserIdAndParameters_errorResponse() {
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request");
        when(restTemplate.exchange(anyString(), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(Object.class), anyMap()))
                .thenThrow(exception);

        ResponseEntity<Object> response = baseClient.patch("/test", 1L, Map.of("key", "value"), null);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(restTemplate).exchange(eq("/test"), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(Object.class), anyMap());
    }

    @Test
    void delete_withUserId_success() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.noContent().build();
        when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<Object> response = baseClient.delete("/test", 1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(restTemplate).exchange(eq("/test"), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Object.class));
    }
}