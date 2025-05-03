package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@SuppressWarnings("unused")
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, ItemNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFound(final RuntimeException e) {
        log.warn("Обнаружена ошибка {} при обработке запроса: возвращаем 404 Не найдено",
                e.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler({EmailDuplicateException.class})
    public ResponseEntity<ApiError> handleEmailAlreadyExists(final EmailDuplicateException e) {
        log.warn("Обнаружена ошибка {} при обработке запроса: возвращаем 409 Конфликт",
                e.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(e.getMessage(), HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<ApiError> handleAccessDenied(final ForbiddenAccessException e) {
        log.warn("Обнаружена ошибка {} при обработке запроса: возвращаем 403 Доступ запрещен",
                e.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiError(e.getMessage(), HttpStatus.FORBIDDEN.value()));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiError> handleMissingHeader(final MissingRequestHeaderException e) {
        log.warn("Обнаружена ошибка {} при обработке запроса: возвращаем 400 Неверный запрос",
                e.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        log.warn("Обнаружена ошибка {} при обработке запроса: возвращаем 400 Неверный запрос",
                e.getClass().getSimpleName());
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMessage = "Ошибка валидации";

        if (fieldError != null) {
            errorMessage = fieldError.getDefaultMessage();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(errorMessage, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(final IllegalArgumentException e) {
        log.warn("Обнаружена ошибка {} при обработке запроса: возвращаем 400 Неверный запрос",
                e.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleGenericException(final RuntimeException e) {
        log.warn("Обнаружена ошибка {} при обработке запроса: возвращаем 500 Внутренняя ошибка сервера",
                e.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}