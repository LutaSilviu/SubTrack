package com.proiect.subtrack.config;

import com.proiect.subtrack.utils.errors.CredentialsNotValidException;
import com.proiect.subtrack.utils.errors.NoActiveSubscriptionsException;
import com.proiect.subtrack.utils.errors.NoUserFoundException;
import com.proiect.subtrack.utils.errors.SubscriptionNotFoundError;
import com.proiect.subtrack.utils.errors.UserAlreadyExistsException;
import com.proiect.subtrack.utils.errors.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserExists(UserAlreadyExistsException ex) {
        log.warn("User already exists exception: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "status", 409
                ));
    }
    @ExceptionHandler(SubscriptionNotFoundError.class)
    public ResponseEntity<?> handleSubscriptionNotFound(SubscriptionNotFoundError ex) {
        log.warn("Subscription not found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "status", 404
                ));
    }

    @ExceptionHandler(NoUserFoundException.class)
    public ResponseEntity<?> handleNoUserFoundException(NoUserFoundException ex) {
        log.warn("No user found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "status", 404
                ));
    }

    @ExceptionHandler(NoActiveSubscriptionsException.class)
    public ResponseEntity<?> handleNoActiveSubscription(NoActiveSubscriptionsException ex) {
        log.warn("No active subscriptions: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "status", 404
                ));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException ex) {
        log.warn("Validation error - Field: {}, Message: {}", ex.getField(), ex.getMessage());

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", 400);
        errorResponse.put("error", "Validation Error");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("timestamp", LocalDateTime.now().toString());

        if (ex.getField() != null) {
            errorResponse.put("field", ex.getField());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(CredentialsNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleCredentialsNotValid(CredentialsNotValidException ex) {
        log.error("Invalid credentials attempt: {}", ex.getMessage());

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", 401);
        errorResponse.put("error", "Unauthorized");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("timestamp", LocalDateTime.now().toString());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Invalid argument: {}", ex.getMessage());

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", 400);
        errorResponse.put("error", "Bad Request");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("timestamp", LocalDateTime.now().toString());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", 500);
        errorResponse.put("error", "Internal Server Error");
        errorResponse.put("message", "A apărut o eroare internă. Vă rugăm încercați mai târziu.");
        errorResponse.put("timestamp", LocalDateTime.now().toString());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }


}
