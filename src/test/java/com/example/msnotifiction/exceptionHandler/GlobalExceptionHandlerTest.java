package com.example.msnotifiction.exception.exceptionHandler;

import com.example.msnotifiction.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void shouldHandleNotFoundException() {
        // Given
        NotFoundException exception = new NotFoundException("Not found", 1L);

        // When
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleNotFound(exception);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldHandleAccessDeniedException() {
        // Given
        AccessDeniedException exception = new AccessDeniedException("Access denied");

        // When
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleAccessDenied(exception);

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}