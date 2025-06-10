package com.example.athletefatiguetracker.dto;

import com.example.athletefatiguetracker.entity.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NotificationResponseDto {
    private Long notificationId;
    private NotificationType type;
    private String message;
    private LocalDateTime createdAt;
    private boolean read;
}
