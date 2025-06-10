package com.example.athletefatiguetracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(nullable = false)
    private Long athleteId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private NotificationType type;

    @Column(length = 255, nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime deliveredAt;   // когда реально отправлено (если пуш/Email)

    @Column(nullable = false)
    private boolean read;                // пользователь просмотрел

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        read = false;
    }
}
