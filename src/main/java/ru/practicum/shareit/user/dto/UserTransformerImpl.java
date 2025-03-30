package ru.practicum.shareit.user.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

@Component
public class UserTransformerImpl implements UserTransformer {
    @Override
    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public User toUser(NewUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getAddress());
        return user;
    }

    @Override
    public User applyUpdates(UpdateUserRequest updates, User user) {
        if (updates.getName() != null) user.setName(updates.getName());
        if (updates.getAddress() != null) user.setEmail(updates.getAddress());
        return user;
    }
}