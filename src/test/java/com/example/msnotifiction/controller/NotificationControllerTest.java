package com.example.msnotifiction.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private com.example.msnotifiction.service.NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void shouldCreateController() {
        assertNotNull(notificationController);
    }
}