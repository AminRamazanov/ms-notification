package com.example.msnotifiction.util;

import com.example.msnotifiction.dao.entity.NotificationEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationUtilTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private NotificationUtil notificationUtil;

    @Test
    void shouldSendSingleNotification() {
        // Given
        NotificationEntity entity = new NotificationEntity();
        entity.setEmail("test@example.com");
        entity.setSubject("Test Subject");
        entity.setText("Test Text");
        entity.setHtml(false);

        // When
        notificationUtil.sendSingleNotification(entity);

        // Then
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void shouldCreateMessage() {
        // When
        SimpleMailMessage message = notificationUtil.createMessage("test@example.com", "Subject", "Text");

        // Then
        assert message != null;
        assert message.getTo()[0].equals("test@example.com");
        assert message.getSubject().equals("Subject");
        assert message.getText().equals("Text");
    }
}