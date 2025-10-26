package com.example.msnotifiction.service;

import com.example.msnotifiction.model.request.*;
import com.example.msnotifiction.model.response.NotificationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {
    Page<NotificationResponseDto> get(Long userId,
                                      Long orderId,
                                      String email,
                                      Pageable pageable);

    void sendUserActivationLink(UserActivate userActivate);

    void sendOrderResultNotification(OrderResultNotificationEvent orderResultNotificationEvent);

    void notifyOrderReadyForPickup(OrderReadyEvent orderReadyEvent);

    void notifyOrderCompletion(OrderCompletedEvent orderCompletedEvent);

    void sendOtpForPasswordRecovery(RecoveryPasswordEvent recoveryPasswordEvent);

    void retryPendingOutboxes();
}
