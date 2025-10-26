package com.example.msnotifiction.eventHandler;

import com.example.msnotifiction.dao.entity.NotificationEntity;
import com.example.msnotifiction.dao.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationProcessorTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationProcessor notificationProcessor;

    @Test
    void shouldMarkNotificationAsProcessed() {
        // Given
        NotificationEntity entity = new NotificationEntity();
        entity.setId(1L);

        // When
        notificationProcessor.markProcessed(entity);

        // Then
        verify(notificationRepository).save(entity);
    }
}