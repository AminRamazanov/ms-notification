package com.example.msnotifiction.model.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class OrderReadyEvent implements Serializable {
    private Long id;
    private Long userId;
    private UUID eventId;
    private String email;
}
