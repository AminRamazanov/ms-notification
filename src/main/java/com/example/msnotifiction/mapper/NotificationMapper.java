package com.example.msnotifiction.mapper;

import com.example.msnotifiction.dao.entity.NotificationEntity;
import com.example.msnotifiction.model.response.NotificationResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationResponseDto toResponseDto(NotificationEntity notificationEntity);
}
