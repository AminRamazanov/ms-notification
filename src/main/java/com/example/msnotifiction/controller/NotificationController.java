package com.example.msnotifiction.controller;

import com.example.msnotifiction.model.request.NotificationRequestDto;
import com.example.msnotifiction.model.response.NotificationResponseDto;
import com.example.msnotifiction.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<NotificationResponseDto>> get(Pageable pageable,
                                                             @RequestParam(required = false) Long userId,
                                                             @RequestParam(required = false) Long orderId,
                                                             @RequestParam(required = false) String email){

        Page<NotificationResponseDto> page = notificationService.get(userId, orderId, email, pageable);
        return ResponseEntity.ok(page);
    }
}
