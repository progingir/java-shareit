package ru.practicum.shareit.user.model;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.EmailDuplicateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserStorageImpl implements UserStorage {
    private final Map<Long, User> storage = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<User> fetchAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Long add(User user) {
        if (emailExists(user.getEmail())) {
            throw new EmailDuplicateException("Пользователь с email " + user.getEmail() + " уже существует", 409);
        }
        Long newId = idGenerator.getAndIncrement();
        user.setId(newId);
        storage.put(newId, user);
        return newId;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public boolean emailExists(String email) {
        return storage.values().stream().anyMatch(u -> u.getEmail().equals(email));
    }

    @Override
    public void modify(User updatedUser) {
        Long userId = updatedUser.getId();
        if (storage.containsKey(userId)) {
            User existing = storage.get(userId);
            existing.setName(updatedUser.getName());
            existing.setEmail(updatedUser.getEmail());
        }
    }

    @Override
    public void remove(Long id) {
        storage.remove(id);
    }
}