package com.example.msnotifiction.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemEvent {
    private Long catalogItemId;
    private String catalogItemName;
    private Integer quantity;
    private BigDecimal price;
}

