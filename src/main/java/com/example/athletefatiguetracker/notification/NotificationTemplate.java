package com.example.athletefatiguetracker.notification;

import com.example.athletefatiguetracker.entity.Notification;
import com.example.athletefatiguetracker.entity.NotificationType;
import com.example.athletefatiguetracker.repository.NotificationRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public abstract class NotificationTemplate {

    private final NotificationRepository repo;

    protected NotificationTemplate(NotificationRepository repo) {
        this.repo = repo;
    }

    /**
     * Основной метод-шаблон: валидация → отправка → сохранение в БД.
     */
    @Transactional
    public void notify(Long athleteId, String message) {
        validate(athleteId, message);
        send(athleteId, message);
        log(athleteId, message);
    }

    /** Шаг 1: валидировать возможность отправки */
    protected abstract void validate(Long athleteId, String message);

    /** Шаг 2: собственно отправить уведомление (Email, Push, SMS) */
    protected abstract void send(Long athleteId, String message);

    /** Шаг 3: сохранить запись в БД через NotificationRepository */
    private void log(Long athleteId, String message) {
        Notification notif = Notification.builder()
                .athleteId(athleteId)
                .type(getNotificationType())
                .message(message)
                .createdAt(LocalDateTime.now())
                .read(false)
                .build();
        repo.save(notif);
    }

    /** Каждый конкретный канал будет задавать свой тип уведомления */
    protected abstract NotificationType getNotificationType();
}
