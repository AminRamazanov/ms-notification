package com.example.msnotifiction.eventHandler;

import com.example.msnotifiction.dao.entity.NotificationEntity;
import com.example.msnotifiction.dao.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NotificationProcessor {

    private final NotificationRepository notificationRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markProcessed(NotificationEntity notificationEntity) {
        notificationEntity.setProcessed(true);
        notificationRepository.save(notificationEntity);
    }
}
