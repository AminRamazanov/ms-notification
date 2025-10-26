package com.example.msnotifiction.dao.repository;

import com.example.msnotifiction.dao.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends
        JpaRepository<NotificationEntity, Long>,
        JpaSpecificationExecutor<NotificationEntity> {
    List<NotificationEntity> findAllByProcessedFalse();
}
