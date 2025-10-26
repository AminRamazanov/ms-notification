
package com.example.msnotifiction.scheduling;

import com.example.msnotifiction.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SchedulerServiceTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private SchedulerService schedulerService;

    @Test
    void shouldRetryPendingOutboxes() {
        // When
        schedulerService.retryPendingOutboxes();

        // Then
        verify(notificationService).retryPendingOutboxes();
    }
}