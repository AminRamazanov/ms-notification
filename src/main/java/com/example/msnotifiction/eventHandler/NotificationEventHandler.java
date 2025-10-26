package com.example.msnotifiction.eventHandler;

import com.example.msnotifiction.dao.entity.NotificationEntity;
import com.example.msnotifiction.dao.repository.NotificationRepository;
import com.example.msnotifiction.exception.NotFoundException;
import com.example.msnotifiction.util.NotificationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventHandler {
    private final NotificationRepository notificationRepository;
    private final NotificationUtil notificationUtil;
    private final NotificationProcessor notificationProcessor;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handle(NotificationEvent event) {
        log.info("Handling NotificationCreatedEvent for id={}", event.notificationId());

        NotificationEntity entity = notificationRepository.findById(event.notificationId())
                .orElseThrow(()-> new NotFoundException("Notification not found for id={}", event.notificationId()));

        try {
            notificationUtil.sendSingleNotification(entity);
            entity.setProcessed(true);
            notificationProcessor.markProcessed(entity);
            log.info("Notification processed and updated for id={}", entity.getId());
        } catch (Exception e) {
            log.error("Error while sending email for id={}", entity.getId(), e);
        }
    }
}
