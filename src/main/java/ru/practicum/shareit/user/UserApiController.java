package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserResponse;
import ru.practicum.shareit.user.model.UserManager;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserApiController {
    private final UserManager userManager;

    @GetMapping
    public ResponseEntity<List<UserResponse>> fetchAllUsers() {
        log.info("Получение списка всех пользователей");
        return ResponseEntity.ok(userManager.fetchAllUsers());
    }

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@RequestBody @Valid NewUserRequest request) {
        log.info("Добавление нового пользователя");
        return ResponseEntity.ok(userManager.addUser(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        log.info("Получение пользователя с ID: {}", id);
        return ResponseEntity.ok(userManager.findUserById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UpdateUserRequest request,
                                                   @PathVariable Long id) {
        log.info("Обновление пользователя с ID: {}", id);
        return ResponseEntity.ok(userManager.modifyUser(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {
        log.info("Удаление пользователя с ID: {}", id);
        userManager.removeUser(id);
        return ResponseEntity.noContent().build();
    }
}