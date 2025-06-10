package com.example.athletefatiguetracker.service;

import com.example.athletefatiguetracker.dto.NotificationDto;
import com.example.athletefatiguetracker.dto.NotificationResponseDto;
import com.example.athletefatiguetracker.entity.Notification;
import com.example.athletefatiguetracker.exception.ResourceNotFoundException;
import com.example.athletefatiguetracker.mapper.NotificationMapper;
import com.example.athletefatiguetracker.repository.NotificationRepository;
import com.example.athletefatiguetracker.service.inter.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final NotificationRepository repo;
    private final NotificationMapper mapper;

    @Override
    @Transactional
    public NotificationResponseDto create(NotificationDto dto) {
        Notification n = Notification.builder()
                .athleteId(dto.getAthleteId())
                .type(dto.getType())
                .message(dto.getMessage())
                .build();
        Notification saved = repo.save(n);
        return mapper.toResponseDto(saved);
    }

    @Override
    public List<NotificationResponseDto> getForAthlete(Long athleteId) {
        return repo.findByAthleteIdOrderByCreatedAtDesc(athleteId)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void markAsRead(Long notificationId) {
        Notification n = repo.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Notification with id " + notificationId + " not found"));
        n.setRead(true);
        repo.save(n);
    }

}
