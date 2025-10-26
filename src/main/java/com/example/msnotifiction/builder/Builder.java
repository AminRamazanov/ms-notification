package com.example.msnotifiction.builder;

import com.example.msnotifiction.dao.entity.NotificationEntity;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class Builder {

    public static NotificationEntity createNotificationEntity(Long orderId, UUID eventId,
                                                              Long userId, String subject,
                                                              String text, String email, Boolean html) {
        return NotificationEntity.builder()
                .orderId(orderId)
                .userId(userId)
                .eventId(eventId)
                .subject(subject)
                .text(text)
                .email(email)
                .html(html)
                .build();
    }

}
