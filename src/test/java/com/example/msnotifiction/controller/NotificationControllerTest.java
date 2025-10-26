package com.example.msnotifiction.controller;

import com.example.msnotifiction.model.response.NotificationResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private com.example.msnotifiction.service.NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void shouldGetNotifications() {
        // Given
        Page<NotificationResponseDto> notificationPage = new PageImpl<>(List.of(new NotificationResponseDto()));
        when(notificationService.get(null, null, null, PageRequest.of(0, 10)))
                .thenReturn(notificationPage);

        // When
        ResponseEntity<Page<NotificationResponseDto>> response =
                notificationController.get(PageRequest.of(0, 10), null, null, null);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        verify(notificationService).get(null, null, null, PageRequest.of(0, 10));
    }
}