package com.example.msnotifiction.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserActivate {
    private final Long id;
    private final String username;
    private final String email;
    private final String key;
}
