package com.example.msnotifiction.messaging;

import com.example.msnotifiction.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageListenerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private MessageListener messageListener;

    @Test
    void shouldCallServiceMethods() {
        // When
        messageListener.userActivate(null);
        messageListener.recoveryPassword(null);
        messageListener.orderResult(null);
        messageListener.orderReady(null);
        messageListener.orderCompleted(null);

        // Then
        verify(notificationService).sendUserActivationLink(null);
        verify(notificationService).sendOtpForPasswordRecovery(null);
        verify(notificationService).sendOrderResultNotification(null);
        verify(notificationService).notifyOrderReadyForPickup(null);
        verify(notificationService).notifyOrderCompletion(null);
    }
}