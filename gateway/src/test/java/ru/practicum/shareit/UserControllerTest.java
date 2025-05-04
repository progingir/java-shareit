package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.user.UserClient;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserClient userClient;

    @InjectMocks
    private UserController userController;

    private NewUserRequest newUserRequest;
    private UpdateUserRequest updateUserRequest;

    @BeforeEach
    void setUp() {
        newUserRequest = new NewUserRequest("John", "john@example.com");
        updateUserRequest = new UpdateUserRequest("John Updated", "john.updated@example.com");
    }

    @Test
    void getUserById() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(new Object());
        when(userClient.getUserById(anyLong())).thenReturn(expectedResponse);

        ResponseEntity<Object> response = userController.getUserById(1L);

        assertEquals(expectedResponse, response);
    }

    @Test
    void createUser() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(new Object());
        when(userClient.addUser(any(NewUserRequest.class))).thenReturn(expectedResponse);

        ResponseEntity<Object> response = userController.createUser(newUserRequest);

        assertEquals(expectedResponse, response);
    }

    @Test
    void update() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(new Object());
        when(userClient.updateUser(anyLong(), any(UpdateUserRequest.class))).thenReturn(expectedResponse);

        ResponseEntity<Object> response = userController.update(1L, updateUserRequest);

        assertEquals(expectedResponse, response);
    }

    @Test
    void deleteUser() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();
        when(userClient.deleteUser(anyLong())).thenReturn(expectedResponse);

        ResponseEntity<Object> response = userController.deleteUser(1L);

        assertEquals(expectedResponse, response);
    }
}