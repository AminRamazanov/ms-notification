package com.example.msnotifiction.messaging;

import com.example.msnotifiction.config.RabbitConfig;
//import com.example.msnotifiction.service.MailService;
import com.example.msnotifiction.model.request.*;
import com.example.msnotifiction.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageListener {
    private final NotificationService notificationService;

    @RabbitListener(queues = RabbitConfig.REGISTRATION_QUEUE)
    public void userActivate(UserActivate userActivate){
        log.info("Action.userActivate.started for user {}", userActivate.getId());
        notificationService.sendUserActivationLink(userActivate);
    }

    @RabbitListener(queues = RabbitConfig.RECOVERY_PASSWORD_QUEUE)
    public void recoveryPassword(RecoveryPasswordEvent recoveryPasswordEvent){
        log.info("Action.log.recoveryPassword started for user {}", recoveryPasswordEvent.getEmail());
        notificationService.sendOtpForPasswordRecovery(recoveryPasswordEvent);
    }

    @RabbitListener(queues = RabbitConfig.ORDER_RESULT_QUEUE)
    public void orderResult(OrderResultNotificationEvent orderResultNotificationEvent){
        log.info("Action.log.orderResult started for user {}", orderResultNotificationEvent.getId());
        notificationService.sendOrderResultNotification(orderResultNotificationEvent);
    }

    @RabbitListener(queues = RabbitConfig.ORDER_READY_QUEUE)
    public void orderReady(OrderReadyEvent orderReadyEvent){
        log.info("Action.log.orderReady started for user {}", orderReadyEvent.getId());
        notificationService.notifyOrderReadyForPickup(orderReadyEvent);
    }

    @RabbitListener(queues = RabbitConfig.ORDER_COMPLETED_QUEUE)
    public void orderCompleted(OrderCompletedEvent orderCompletedEvent){
        log.info("Action.log.orderCompleted started for user {}", orderCompletedEvent.getId());
        notificationService.notifyOrderCompletion(orderCompletedEvent);
    }

}
