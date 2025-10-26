package com.example.msnotifiction.model.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderResultNotificationEvent implements Serializable {
    private Long id;
    private Long userId;
    private UUID eventId;
    private String email;
    private List<OrderItemEvent> items;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private String status;
    private String message;

}
