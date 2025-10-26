package com.example.msnotifiction.messaging;

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
    void shouldHandleUserActivate() {
        // When
        messageListener.userActivate(null);

        // Then
        verify(notificationService).sendUserActivationLink(null);
    }

    @Test
    void shouldHandleRecoveryPassword() {
        // When
        messageListener.recoveryPassword(null);

        // Then
        verify(notificationService).sendOtpForPasswordRecovery(null);
    }

    @Test
    void shouldHandleOrderResult() {
        // When
        messageListener.orderResult(null);

        // Then
        verify(notificationService).sendOrderResultNotification(null);
    }

    @Test
    void shouldHandleOrderReady() {
        // When
        messageListener.orderReady(null);

        // Then
        verify(notificationService).notifyOrderReadyForPickup(null);
    }

    @Test
    void shouldHandleOrderCompleted() {
        // When
        messageListener.orderCompleted(null);

        // Then
        verify(notificationService).notifyOrderCompletion(null);
    }
}