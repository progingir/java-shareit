package ru.practicum.shareit.user.model;

import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserResponse;

import java.util.List;

public interface UserManager {
    List<UserResponse> fetchAllUsers();

    UserResponse addUser(NewUserRequest request);

    UserResponse findUserById(Long id);

    UserResponse modifyUser(UpdateUserRequest request, Long userId);

    void removeUser(Long id);
}