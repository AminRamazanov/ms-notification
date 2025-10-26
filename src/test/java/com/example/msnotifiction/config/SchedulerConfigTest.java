package com.example.msnotifiction.config;

import net.javacrumbs.shedlock.core.LockProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SchedulerConfigTest {

    @Mock
    private RedisConnectionFactory redisConnectionFactory;

    @Test
    void shouldCreateLockProvider() {
        // Given
        SchedulerConfig schedulerConfig = new SchedulerConfig();

        // When
        LockProvider lockProvider = schedulerConfig.lockProvider(redisConnectionFactory);

        // Then
        assertThat(lockProvider).isNotNull();
    }
}