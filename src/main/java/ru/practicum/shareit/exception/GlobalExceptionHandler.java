package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
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
        return ResponseEntity.status(404).body(new ApiError(e.getMessage(), 404));
    }

    @ExceptionHandler({EmailDuplicateException.class})
    public ResponseEntity<EmailDuplicateException> handleEmailAlreadyExists(final RuntimeException e) {
        log.warn("Обнаружена ошибка {} при обработке запроса: возвращаем 409 Конфликт",
                e.getClass().getSimpleName());
        return ResponseEntity.status(409).body(new EmailDuplicateException(e.getMessage(), 409));
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<ApiError> handleAccessDenied(final ForbiddenAccessException e) {
        log.warn("Обнаружена ошибка {} при обработке запроса: возвращаем 403 Доступ запрещен",
                e.getClass().getSimpleName());
        return ResponseEntity.status(403).body(new ApiError(e.getMessage(), 403));
    }

    @ExceptionHandler({MissingRequestHeaderException.class})
    public ResponseEntity<ApiError> handleMissingHeader(final MissingRequestHeaderException e) {
        log.warn("Обнаружена ошибка {} при обработке запроса: возвращаем 400 Неверный запрос",
                e.getClass().getSimpleName());
        return ResponseEntity.status(400).body(new ApiError(e.getMessage(), 400));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e) {
        log.warn("Обнаружена ошибка {} при обработке запроса: возвращаем 400 Неверный запрос",
                e.getClass().getSimpleName());
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMessage = "Ошибка валидации";
        if (fieldError != null) {
            errorMessage = fieldError.getDefaultMessage();
        }
        return ResponseEntity.status(400).body(new ApiError(errorMessage, 400));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleGenericException(final RuntimeException e) {
        log.warn("Обнаружена ошибка {} при обработке запроса: возвращаем 500 Внутренняя ошибка сервера",
                e.getClass().getSimpleName());
        return ResponseEntity.status(500).body(new ApiError(e.getMessage(), 500));
    }
}