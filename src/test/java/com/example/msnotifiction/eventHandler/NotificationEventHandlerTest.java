package com.example.msnotifiction.eventHandler;

import com.example.msnotifiction.dao.entity.NotificationEntity;
import com.example.msnotifiction.dao.repository.NotificationRepository;
import com.example.msnotifiction.util.NotificationUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationEventHandlerTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationUtil notificationUtil;

    @Mock
    private NotificationProcessor notificationProcessor;

    @InjectMocks
    private NotificationEventHandler notificationEventHandler;

    @Test
    void shouldHandleNotificationEvent() {
        // Given
        Long notificationId = 1L;
        NotificationEvent event = new NotificationEvent(notificationId);
        NotificationEntity entity = new NotificationEntity();
        entity.setId(notificationId);

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(entity));

        // When
        notificationEventHandler.handle(event);

        // Then
        verify(notificationRepository).findById(notificationId);
        verify(notificationUtil).sendSingleNotification(entity);
        verify(notificationProcessor).markProcessed(entity);
    }
}