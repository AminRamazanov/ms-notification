package com.example.msnotifiction.util;

import com.example.msnotifiction.dao.entity.NotificationEntity;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationUtil {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;


    public SimpleMailMessage createMessage(String email, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(email);
        mailMessage.setFrom(from);

        mailMessage.setSubject(subject);
        mailMessage.setText(text);

        return mailMessage;
    }

    @SneakyThrows
    private void sendHtmlEmail(String to, String subject, String htmlContent) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        helper.setFrom(from);
        mailSender.send(mimeMessage);
    }

    public void sendSingleNotification(NotificationEntity entity) {
        if (Boolean.TRUE.equals(entity.getHtml())) {
            sendHtmlEmail(entity.getEmail(), entity.getSubject(), entity.getText());
        } else {
            SimpleMailMessage message = createMessage(entity.getEmail(), entity.getSubject(), entity.getText());
            mailSender.send(message);
        }
    }

}

