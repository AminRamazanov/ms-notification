package com.example.msnotifiction.service;

import com.example.msnotifiction.dao.entity.NotificationEntity;
import com.example.msnotifiction.dao.repository.NotificationRepository;
import com.example.msnotifiction.mapper.NotificationMapper;
import com.example.msnotifiction.model.response.NotificationResponseDto;
import com.example.msnotifiction.properties.MessagePattern;
import com.example.msnotifiction.service.impl.NotificationServiceImpl;
import com.example.msnotifiction.eventHandler.NotificationEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private MessagePattern messagePattern;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private NotificationMapper notificationMapper;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    void shouldGetNotifications() {
        // Given
        PageRequest pageable = PageRequest.of(0, 10);
        NotificationEntity entity = new NotificationEntity();
        NotificationResponseDto dto = new NotificationResponseDto();
        Page<NotificationEntity> entityPage = new PageImpl<>(List.of(entity), pageable, 1);

        when(notificationRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(entityPage);
        when(notificationMapper.toResponseDto(entity)).thenReturn(dto);

        // When
        Page<NotificationResponseDto> result = notificationService.get(null, null, null, pageable);

        // Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(notificationRepository).findAll(any(Specification.class), eq(pageable));
        verify(notificationMapper).toResponseDto(entity);
    }

    @Test
    void shouldSendUserActivationLink() {
        // Given
        when(notificationRepository.save(any(NotificationEntity.class))).thenReturn(new NotificationEntity());

        // When
        notificationService.sendUserActivationLink(null);

        // Then
        verify(messagePattern).userActivate(any());
        verify(notificationRepository).save(any(NotificationEntity.class));
        verify(applicationEventPublisher).publishEvent(any(NotificationEvent.class));
    }

    @Test
    void shouldSendOrderResultNotification() {
        // Given
        when(notificationRepository.save(any(NotificationEntity.class))).thenReturn(new NotificationEntity());

        // When
        notificationService.sendOrderResultNotification(null);

        // Then
        verify(messagePattern).orderResultEvent(any());
        verify(notificationRepository).save(any(NotificationEntity.class));
        verify(applicationEventPublisher).publishEvent(any(NotificationEvent.class));
    }

    @Test
    void shouldNotifyOrderReadyForPickup() {
        // Given
        when(notificationRepository.save(any(NotificationEntity.class))).thenReturn(new NotificationEntity());

        // When
        notificationService.notifyOrderReadyForPickup(null);

        // Then
        verify(messagePattern).notifyOrder(any());
        verify(notificationRepository).save(any(NotificationEntity.class));
        verify(applicationEventPublisher).publishEvent(any(NotificationEvent.class));
    }

    @Test
    void shouldRetryPendingOutboxes() {
        // Given
        NotificationEntity pendingNotification = new NotificationEntity();
        pendingNotification.setId(1L);
        List<NotificationEntity> pendingList = List.of(pendingNotification);

        when(notificationRepository.findAllByProcessedFalse()).thenReturn(pendingList);

        // When
        notificationService.retryPendingOutboxes();

        // Then
        verify(notificationRepository).findAllByProcessedFalse();
        verify(applicationEventPublisher, atLeastOnce()).publishEvent(any(NotificationEvent.class));
    }
}