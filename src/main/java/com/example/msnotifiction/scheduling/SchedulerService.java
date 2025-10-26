package com.example.msnotifiction.scheduling;

import com.example.msnotifiction.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class SchedulerService {
    private final NotificationService notificationService;

    @Scheduled(fixedDelay = 30000)
    @Transactional
    @SchedulerLock(
            name = "retryPendingOutboxes",
            lockAtMostFor = "PT35S",
            lockAtLeastFor = "PT5S"
    )
    public void retryPendingOutboxes() {
        notificationService.retryPendingOutboxes();
    }
}
