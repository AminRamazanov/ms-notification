package com.example.msnotifiction.service.impl;

import com.example.msnotifiction.builder.Builder;
import com.example.msnotifiction.dao.entity.NotificationEntity;
import com.example.msnotifiction.dao.repository.NotificationRepository;
import com.example.msnotifiction.mapper.NotificationMapper;
import com.example.msnotifiction.model.request.*;
import com.example.msnotifiction.model.response.NotificationResponseDto;
import com.example.msnotifiction.properties.MessagePattern;
import com.example.msnotifiction.service.NotificationService;
import com.example.msnotifiction.specification.NotificationSpecification;
import com.example.msnotifiction.eventHandler.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final MessagePattern messagePattern;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final NotificationMapper notificationMapper;


    @Override
    public Page<NotificationResponseDto> get(Long userId,
                                             Long orderId,
                                             String email,
                                             Pageable pageable) {
        log.info("Action.log.get started");
        var specification = NotificationSpecification.filter(userId, orderId, email);

        Page<NotificationEntity> notificationEntityPage = notificationRepository.findAll(
                specification,
                pageable);

        List<NotificationResponseDto> notificationResponseDtoList = notificationEntityPage
                .stream()
                .map(notificationMapper::toResponseDto)
                .toList();

        Page<NotificationResponseDto> notificationResponseDtoPage = new PageImpl<>(
                notificationResponseDtoList,
                pageable,
                notificationEntityPage.getTotalElements()
        );
        log.info("Action.log.get ended");
        return notificationResponseDtoPage;
    }

    @Override
    public void sendUserActivationLink(UserActivate userActivate) {
        log.info("Action.log.sendUserActivationLink started for user {}", userActivate.getId());

        MessagePattern completeMessage = messagePattern.userActivate(userActivate);

        NotificationEntity notificationEntity = Builder.createNotificationEntity(
                null,
                null,
                userActivate.getId(),
                completeMessage.getSubject(),
                completeMessage.getText(),
                userActivate.getEmail(),
                false
        );

        notificationRepository.save(notificationEntity);
        applicationEventPublisher.publishEvent(new NotificationEvent(notificationEntity.getId()));
        log.info("Action.sendUserActivationLink ended for user {}", userActivate.getId());
    }

    @Override
    @SneakyThrows
    public void sendOrderResultNotification(OrderResultNotificationEvent orderResultNotificationEvent) {
        log.info("Action.log.sendOrderResultNotification started for order {}", orderResultNotificationEvent.getId());

        MessagePattern completedMessage = messagePattern.orderResultEvent(orderResultNotificationEvent);


        NotificationEntity notificationEntity = Builder.createNotificationEntity(
                orderResultNotificationEvent.getId(),
                orderResultNotificationEvent.getEventId(),
                orderResultNotificationEvent.getUserId(),
                completedMessage.getSubject(),
                completedMessage.getText(),
                orderResultNotificationEvent.getEmail(),
                true
        );


        notificationRepository.save(notificationEntity);
        applicationEventPublisher.publishEvent(new NotificationEvent(notificationEntity.getId()));
        log.info("Action.log.sendOrderResultNotification ended for order {}", orderResultNotificationEvent.getId());
    }

    @Override
    public void notifyOrderReadyForPickup(OrderReadyEvent orderReadyEvent) {
        log.info("Action.log.notifyOrderReadyForPickup started for order {}", orderReadyEvent.getId());
        MessagePattern completedMessage = messagePattern.notifyOrder(orderReadyEvent);

        NotificationEntity notificationEntity = Builder.createNotificationEntity(
                orderReadyEvent.getId(),
                orderReadyEvent.getEventId(),
                orderReadyEvent.getUserId(),
                completedMessage.getSubject(),
                completedMessage.getText(),
                orderReadyEvent.getEmail(),
                false
        );

        notificationEntity.setHtml(false);
        notificationRepository.save(notificationEntity);
        applicationEventPublisher.publishEvent(new NotificationEvent(notificationEntity.getId()));
        log.info("Action.log.notifyOrderReadyForPickup ended for order {}", orderReadyEvent.getId());
    }

    @Override
    public void notifyOrderCompletion(OrderCompletedEvent orderCompletedEvent) {
        log.info("Action.log.notifyOrderCompletion started for order {}", orderCompletedEvent.getId());

        MessagePattern completedMessage = messagePattern.orderCompletedMessage(orderCompletedEvent);

        NotificationEntity notificationEntity = Builder.createNotificationEntity(
                orderCompletedEvent.getId(),
                orderCompletedEvent.getEventId(),
                orderCompletedEvent.getUserId(),
                completedMessage.getSubject(),
                completedMessage.getText(),
                orderCompletedEvent.getEmail(),
                false
        );

        notificationEntity.setHtml(false);
        notificationRepository.save(notificationEntity);
        applicationEventPublisher.publishEvent(new NotificationEvent(notificationEntity.getId()));
        log.info("Action.log.notifyOrderCompletion ended for order {}", orderCompletedEvent.getId());
    }


    @Override
    public void sendOtpForPasswordRecovery(RecoveryPasswordEvent recoveryPasswordEvent) {
        log.info("Action.log.sendOtpForPasswordRecovery started for email {}", recoveryPasswordEvent.getEmail());
        MessagePattern completedMessage = messagePattern.otpRecoveryMessage(recoveryPasswordEvent);

        NotificationEntity notificationEntity = Builder.createNotificationEntity(
                null,
                null,
                null,
                completedMessage.getSubject(),
                completedMessage.getText(),
                recoveryPasswordEvent.getEmail(),
                false
        );

        notificationEntity.setHtml(false);
        notificationRepository.save(notificationEntity);
        applicationEventPublisher.publishEvent(new NotificationEvent(notificationEntity.getId()));
        log.info("Action.log.sendOtpForPasswordRecovery ended for email {}", recoveryPasswordEvent.getEmail());
    }

    @Override
    public void retryPendingOutboxes() {
        log.info("Action.log.retryPendingOutboxes for notification service started");
        List<NotificationEntity> pending = notificationRepository.findAllByProcessedFalse();

        for (NotificationEntity notification : pending) {
            try {
                applicationEventPublisher.publishEvent(new NotificationEvent(notification.getId()));
                log.info("Retried notification id={}", notification.getId());
            } catch (Exception e) {
                log.error("Failed to retry notification id={}", notification.getId(), e);
            }
        }
    }
}