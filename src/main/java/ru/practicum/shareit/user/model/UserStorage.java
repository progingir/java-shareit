package ru.practicum.shareit.user.model;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    List<User> fetchAll();

    Long add(User user);

    Optional<User> findById(Long id);

    boolean emailExists(String email);

    void modify(User user);

    void remove(Long id);
}