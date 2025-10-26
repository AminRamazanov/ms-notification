package com.example.msnotifiction.mapper;

import com.example.msnotifiction.dao.entity.NotificationEntity;
import com.example.msnotifiction.model.response.NotificationResponseDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class NotificationMapperTest {

    private final NotificationMapper notificationMapper = Mappers.getMapper(NotificationMapper.class);

    @Test
    void shouldMapEntityToResponseDto() {
        NotificationEntity entity = new NotificationEntity();
        NotificationResponseDto dto = notificationMapper.toResponseDto(entity);
        assertNotNull(dto);
    }
}