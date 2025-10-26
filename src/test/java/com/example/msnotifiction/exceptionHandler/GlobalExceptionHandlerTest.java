package com.example.msnotifiction.exceptionHandler;

import com.example.msnotifiction.exception.InvalidOrderStatusException;
import com.example.msnotifiction.exception.NotFoundException;
import com.example.msnotifiction.exception.exceptionHandler.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void shouldHandleNotFoundException() {
        // Given
        NotFoundException exception = new NotFoundException("Notification not found", 1L);

        // When
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleNotFound(exception);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Notification not found", response.getBody().get("message"));
        assertEquals(404, response.getBody().get("status"));
        assertNotNull(response.getBody().get("timestamp"));
    }

    @Test
    void shouldHandleInvalidOrderStatusException() {
        // Given
        InvalidOrderStatusException exception = new InvalidOrderStatusException("Invalid status");

        // When
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleInvalidStatus(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid status", response.getBody().get("message"));
        assertEquals(400, response.getBody().get("status"));
    }

    @Test
    void shouldHandleAccessDeniedException() {
        // Given
        AccessDeniedException exception = new AccessDeniedException("Access denied");

        // When
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleAccessDenied(exception);

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertTrue(response.getBody().get("message").toString().contains("permission"));
        assertEquals(403, response.getBody().get("status"));
    }

    @Test
    void shouldHandleAuthenticationException() {
        // Given
        AuthenticationException exception = new AuthenticationException("Authentication failed") {};

        // When
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleAuthenticationException(exception);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().get("message").toString().contains("authenticated"));
        assertEquals(401, response.getBody().get("status"));
    }
}