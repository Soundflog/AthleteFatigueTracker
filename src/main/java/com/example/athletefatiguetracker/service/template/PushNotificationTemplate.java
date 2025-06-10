package com.example.athletefatiguetracker.service.template;

import com.example.athletefatiguetracker.entity.NotificationType;
import com.example.athletefatiguetracker.repository.NotificationRepository;
import org.springframework.stereotype.Component;

@Component
public class PushNotificationTemplate extends NotificationTemplate {

    public PushNotificationTemplate(NotificationRepository repo) {
        super(repo);
    }

    @Override
    protected void validate(Long athleteId, String message) {
        // Проверить, что у пользователя есть токен для push
    }

    @Override
    protected void send(Long athleteId, String message) {
        // Вызов внешнего Push-сервиса
        System.out.println("Sending PUSH to athlete " + athleteId + ": " + message);
    }

    @Override
    protected NotificationType getNotificationType() {
        return NotificationType.ALERT;
    }
}
