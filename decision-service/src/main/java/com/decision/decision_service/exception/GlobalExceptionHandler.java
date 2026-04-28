package com.decision.decision_service.exception;

import com.decision.decision_service.util.CorrelationIdHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // ── 400 — Validation failures ─────────────────────────────────────────────
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex) {

        String correlationId = CorrelationIdHolder.get();

        // collect all field-level validation errors
        List<Map<String, String>> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", getErrorMessage(error)
                ))
                .toList();

        log.warn("Validation failed — correlationId={} errors={}", correlationId, fieldErrors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "correlation_id", correlationId,
                        "error_code", "INVALID_REQUEST",
                        "message", "Request validation failed",
                        "field_errors", fieldErrors,
                        "timestamp", Instant.now().toString()
                ));
    }

    private String getErrorMessage(FieldError error) {
        // use the validation annotation message if available
        // fall back to a generic message if null
        return error.getDefaultMessage() != null
                ? error.getDefaultMessage()
                : "Invalid value";
    }

    // ── 404 — Resource not found ──────────────────────────────────────────────
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(
            ResourceNotFoundException ex) {

        String correlationId = CorrelationIdHolder.get();

        log.warn("Resource not found — correlationId={} message={}",
                correlationId, ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "correlation_id", correlationId,
                        "error_code", "RECORD_NOT_FOUND",
                        "message", ex.getMessage(),
                        "timestamp", Instant.now().toString()
                ));
    }

    // ── 500 — Catch-all for unexpected exceptions ─────────────────────────────
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {

        String correlationId = CorrelationIdHolder.get();

        // log at ERROR level with full stack trace
        // this is unexpected — we want to know about it
        log.error("Unexpected error — correlationId={}", correlationId, ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "correlation_id", correlationId,
                        "error_code", "INTERNAL_ERROR",
                        // never expose the actual exception message in production
                        // it might contain sensitive information
                        "message", "An unexpected error occurred",
                        "timestamp", Instant.now().toString()
                ));
    }
}
