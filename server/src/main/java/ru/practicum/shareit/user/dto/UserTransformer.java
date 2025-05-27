package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.model.User;

public interface UserTransformer {
    UserResponse toResponse(User user);

    User toUser(NewUserRequest request);

    User applyUpdates(UpdateUserRequest updates, User user);
}