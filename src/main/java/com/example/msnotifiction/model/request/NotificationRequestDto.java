package com.example.msnotifiction.model.request;

import lombok.Data;

@Data
public class NotificationRequestDto {
    private Long userId;
    private Long orderId;
    private String email;
}
