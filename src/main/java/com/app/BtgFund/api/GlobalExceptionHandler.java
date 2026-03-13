package com.app.BtgFund.api;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.BtgFund.common.BusinessException;
import com.app.BtgFund.common.ResourceNotFoundException;
import com.app.BtgFund.common.UnauthorizedException;

@RestControllerAdvice
/**
 * Centralized translation of framework and domain exceptions into HTTP responses.
 */
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    /**
     * Handles bean-validation failures and returns field error details.
     */
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        String details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return build(HttpStatus.BAD_REQUEST, "Validation error", details);
    }

    @ExceptionHandler({ UnauthorizedException.class, BadCredentialsException.class })
    /**
     * Handles authentication and authorization failures.
     */
    public ResponseEntity<Map<String, Object>> handleUnauthorized(RuntimeException ex) {
        return build(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    /**
     * Handles resource-not-found scenarios.
     */
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "Not found", ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    /**
     * Handles business-rule violations.
     */
    public ResponseEntity<Map<String, Object>> handleBusiness(BusinessException ex) {
        return build(HttpStatus.BAD_REQUEST, "Business rule violation", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    /**
     * Fallback handler for uncaught exceptions.
     */
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex.getMessage());
    }

    /**
     * Builds the standard error response payload.
     */
    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String error, String message) {
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", Instant.now().toString(),
                "status", status.value(),
                "error", error,
                "message", message));
    }
}
