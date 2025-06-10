package com.example.athletefatiguetracker.mapper;

import com.example.athletefatiguetracker.dto.NotificationResponseDto;
import com.example.athletefatiguetracker.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public NotificationResponseDto toResponseDto(Notification n) {
        return NotificationResponseDto.builder()
                .notificationId(n.getNotificationId())
                .type(n.getType())
                .message(n.getMessage())
                .createdAt(n.getCreatedAt())
                .read(n.isRead())
                .build();
    }
}
