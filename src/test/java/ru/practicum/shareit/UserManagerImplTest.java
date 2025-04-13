package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserResponse;
import ru.practicum.shareit.user.dto.UserTransformer;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserManagerImpl;
import ru.practicum.shareit.user.model.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserManagerImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTransformer transformer;

    @InjectMocks
    private UserManagerImpl userManager;

    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        userResponse = new UserResponse(1L, "Test User", "test@example.com");
    }

    @Test
    void fetchAllUsers_whenUsersExist_returnsList() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(transformer.toResponse(user)).thenReturn(userResponse);

        List<UserResponse> result = userManager.fetchAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userResponse, result.get(0));
        verify(userRepository, times(1)).findAll();
        verify(transformer, times(1)).toResponse(user);
    }

    @Test
    void fetchAllUsers_whenNoUsers_returnsEmptyList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<UserResponse> result = userManager.fetchAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
        verify(transformer, never()).toResponse(any());
    }

    @Test
    void addUser_whenValidRequest_returnsUserResponse() {
        NewUserRequest request = new NewUserRequest("Test User", "test@example.com");
        when(transformer.toUser(request)).thenReturn(user);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);
        when(transformer.toResponse(user)).thenReturn(userResponse);

        UserResponse result = userManager.addUser(request);

        assertNotNull(result);
        assertEquals(userResponse, result);
        verify(transformer, times(1)).toUser(request);
        verify(userRepository, times(1)).existsByEmail("test@example.com");
        verify(userRepository, times(1)).save(user);
        verify(transformer, times(1)).toResponse(user);
    }


    @Test
    void findUserById_whenUserExists_returnsUserResponse() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(transformer.toResponse(user)).thenReturn(userResponse);

        UserResponse result = userManager.findUserById(1L);

        assertNotNull(result);
        assertEquals(userResponse, result);
        verify(userRepository, times(1)).findById(1L);
        verify(transformer, times(1)).toResponse(user);
    }

    @Test
    void findUserById_whenUserNotFound_throwsUserNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userManager.findUserById(1L));

        assertEquals("Пользователь с ID 1 не найден", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verify(transformer, never()).toResponse(any());
    }

    @Test
    void modifyUser_whenValidRequest_returnsUpdatedUserResponse() {
        UpdateUserRequest request = new UpdateUserRequest("Updated User", "updated@example.com");
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setName("Updated User");
        updatedUser.setEmail("updated@example.com");
        UserResponse updatedResponse = new UserResponse(1L, "Updated User", "updated@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("updated@example.com")).thenReturn(false);
        when(transformer.applyUpdates(request, user)).thenReturn(updatedUser);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        when(transformer.toResponse(updatedUser)).thenReturn(updatedResponse);

        UserResponse result = userManager.modifyUser(request, 1L);

        assertNotNull(result);
        assertEquals(updatedResponse, result);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).existsByEmail("updated@example.com");
        verify(transformer, times(1)).applyUpdates(request, user);
        verify(userRepository, times(1)).save(updatedUser);
        verify(transformer, times(1)).toResponse(updatedUser);
    }

    @Test
    void removeUser_whenUserExists_deletesUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userManager.removeUser(1L);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}