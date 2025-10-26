package com.example.msnotifiction.mapper;

import com.example.msnotifiction.dao.entity.NotificationEntity;
import com.example.msnotifiction.model.response.NotificationResponseDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class NotificationMapperTest {

    private final NotificationMapper notificationMapper = Mappers.getMapper(NotificationMapper.class);

    @Test
    void shouldMapEntityToResponseDto() {
        // Given
        NotificationEntity entity = new NotificationEntity();
        entity.setId(1L);
        entity.setUserId(123L);
        entity.setOrderId(456L);
        entity.setEmail("test@example.com");
        entity.setText("Test notification message");
        entity.setSubject("ORDER_STATUS");

        // When
        NotificationResponseDto dto = notificationMapper.toResponseDto(entity);

        // Then
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(123L, dto.getUserId());
        assertEquals(456L, dto.getOrderId());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("Test notification message", dto.getText());
        assertEquals("ORDER_STATUS", dto.getSubject());
    }

    @Test
    void shouldHandleNullEntity() {
        // When
        NotificationResponseDto dto = notificationMapper.toResponseDto(null);

        // Then
        assertNull(dto);
    }
}