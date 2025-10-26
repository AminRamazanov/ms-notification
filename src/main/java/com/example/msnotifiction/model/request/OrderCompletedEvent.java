package com.example.msnotifiction.model.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderCompletedEvent {
    private Long id;
    private Long userId;
    private UUID eventId;
    private String email;
    private BigDecimal totalPrice;
}
