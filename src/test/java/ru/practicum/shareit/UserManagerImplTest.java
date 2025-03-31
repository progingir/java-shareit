package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.shareit.exception.EmailDuplicateException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.*;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserManagerImpl;
import ru.practicum.shareit.user.model.UserStorage;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserManagerImplTest {

    @Mock
    private UserStorage userStorage;

    @Mock
    private UserTransformer transformer;

    @InjectMocks
    private UserManagerImpl userManager;

    private User testUser;
    private NewUserRequest newUserRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");

        newUserRequest = new NewUserRequest("Test User", "test@example.com");
        userResponse = new UserResponse(1L, "Test User", "test@example.com");
    }

    @Test
    void shouldReturnAllUsersWhenFetchingAll() {
        when(userStorage.fetchAll()).thenReturn(List.of(testUser));
        when(transformer.toResponse(testUser)).thenReturn(userResponse);

        List<UserResponse> result = userManager.fetchAllUsers();

        assertEquals(1, result.size());
        assertEquals(userResponse, result.get(0));
        verify(userStorage).fetchAll();
    }

    @Test
    void shouldAddUserSuccessfully() {
        when(transformer.toUser(newUserRequest)).thenReturn(testUser);
        when(userStorage.add(testUser)).thenReturn(1L);
        when(transformer.toResponse(testUser)).thenReturn(userResponse);

        UserResponse result = userManager.addUser(newUserRequest);

        assertEquals(userResponse, result);
        verify(userStorage).add(testUser);
    }

    @Test
    void shouldModifyUserWhenValidRequest() {
        UpdateUserRequest updateRequest = new UpdateUserRequest("New Name", null);
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setName("New Name");
        updatedUser.setEmail("test@example.com");

        when(userStorage.findById(1L)).thenReturn(Optional.of(testUser));
        when(userStorage.emailExists(any())).thenReturn(false);
        when(transformer.applyUpdates(updateRequest, testUser)).thenReturn(updatedUser);
        when(transformer.toResponse(updatedUser)).thenReturn(new UserResponse(1L, "New Name", "test@example.com"));

        UserResponse result = userManager.modifyUser(updateRequest, 1L);

        assertEquals("New Name", result.getName());
        verify(userStorage).modify(updatedUser);
    }

    @Test
    void shouldThrowEmailDuplicateExceptionWhenModifyingWithDuplicateEmail() {
        UpdateUserRequest updateRequest = new UpdateUserRequest(null, "new@example.com");
        when(userStorage.findById(1L)).thenReturn(Optional.of(testUser));
        when(userStorage.emailExists("new@example.com")).thenReturn(true);

        assertThrows(EmailDuplicateException.class, () -> userManager.modifyUser(updateRequest, 1L));
        verify(userStorage, never()).modify(any());
    }

    @Test
    void shouldRemoveUserSuccessfully() {
        when(userStorage.findById(1L)).thenReturn(Optional.of(testUser));

        userManager.removeUser(1L);

        verify(userStorage).remove(1L);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenRemovingNonExistentUser() {
        when(userStorage.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userManager.removeUser(1L));
        verify(userStorage, never()).remove(any());
    }
}