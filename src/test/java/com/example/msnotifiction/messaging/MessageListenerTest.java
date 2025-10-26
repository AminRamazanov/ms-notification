package com.example.msnotifiction.messaging;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class MessageListenerTest {

    @InjectMocks
    private MessageListener messageListener;

    @Test
    void shouldCreateMessageListener() {
        // Sadəcə object-in yaradıldığını yoxlayırıq
        assertNotNull(messageListener);
    }
}