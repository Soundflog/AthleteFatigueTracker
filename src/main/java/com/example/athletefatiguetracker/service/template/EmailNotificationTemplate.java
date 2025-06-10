package com.example.athletefatiguetracker.service.template;


import com.example.athletefatiguetracker.entity.NotificationType;
import com.example.athletefatiguetracker.repository.NotificationRepository;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationTemplate extends NotificationTemplate {

    public EmailNotificationTemplate(NotificationRepository repo) {
        super(repo);
    }

    @Override
    protected void validate(Long athleteId, String message) {
        // Можно проверить, что у спортсмена есть email в профиле
    }

    @Override
    protected void send(Long athleteId, String message) {
        // Вызов внешнего Email-сервиса
        System.out.println("Sending EMAIL to athlete " + athleteId + ": " + message);
    }

    @Override
    protected NotificationType getNotificationType() {
        return NotificationType.INFO; // или другой тип
    }
}
