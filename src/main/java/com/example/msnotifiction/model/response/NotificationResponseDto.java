package com.example.msnotifiction.model.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class NotificationResponseDto {
    private Long id;

    private Long userId;
    private Long orderId;
    private String email;
    private UUID eventId;

    private String subject;

    private String text;

    private LocalDateTime createdAt;

    private Boolean html;

    private Boolean processed = false;
}
