package com.example.athletefatiguetracker.service.inter;

import com.example.athletefatiguetracker.dto.NotificationDto;
import com.example.athletefatiguetracker.dto.NotificationResponseDto;

import java.util.List;

public interface INotificationService {

    NotificationResponseDto create(NotificationDto dto);
    List<NotificationResponseDto> getForAthlete(Long athleteId);

    void markAsRead(Long notificationId);
}
