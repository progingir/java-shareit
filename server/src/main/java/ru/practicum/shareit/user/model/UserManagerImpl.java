package ru.practicum.shareit.user.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EmailDuplicateException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserResponse;
import ru.practicum.shareit.user.dto.UserTransformer;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserManagerImpl implements UserManager {
    private final UserRepository userRepository;
    private final UserTransformer transformer;

    @Override
    public List<UserResponse> fetchAllUsers() {
        List<UserResponse> users = userRepository.findAll().stream()
                .map(transformer::toResponse)
                .toList();
        log.debug("Получено {} пользователей", users.size());
        return users;
    }

    @Override
    public UserResponse addUser(NewUserRequest request) {
        User user = transformer.toUser(request);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailDuplicateException("Пользователь с email " + user.getEmail() + " уже существует", 409);
        }
        User savedUser = userRepository.save(user);
        log.debug("Добавлен новый пользователь: {}", savedUser);
        return transformer.toResponse(savedUser);
    }

    @Override
    public UserResponse findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Пользователь с ID {} не найден", id);
                    return new UserNotFoundException("Пользователь с ID " + id + " не найден");
                });
        return transformer.toResponse(user);
    }

    @Override
    public UserResponse modifyUser(UpdateUserRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Пользователь с ID {} не найден для обновления", userId);
                    return new UserNotFoundException("Пользователь с ID " + userId + " не найден");
                });
        String newEmail = request.getEmail();
        if (newEmail != null && !newEmail.equals(user.getEmail()) && userRepository.existsByEmail(newEmail)) {
            throw new EmailDuplicateException("Пользователь с email " + newEmail + " уже существует", 409);
        }
        User updatedUser = transformer.applyUpdates(request, user);
        updatedUser = userRepository.save(updatedUser);
        log.debug("Обновлен пользователь: {}", updatedUser);
        return transformer.toResponse(updatedUser);
    }

    @Override
    public void removeUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Пользователь с ID {} не найден для удаления", id);
                    return new UserNotFoundException("Пользователь с ID " + id + " не найден");
                });
        userRepository.deleteById(id);
        log.debug("Удален пользователь с ID {}", id);
    }
}