package com.example.athletefatiguetracker.scheduler;

import com.example.athletefatiguetracker.dto.NotificationDto;
import com.example.athletefatiguetracker.entity.FatiguePrediction;
import com.example.athletefatiguetracker.entity.NotificationType;
import com.example.athletefatiguetracker.service.FatiguePredictionService;
import com.example.athletefatiguetracker.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final FatiguePredictionService predService;
    private final NotificationService notifyService;
    private final Double ALERT_THRESHOLD = 0.8;  // например, индекс ≥0.8

    @Scheduled(fixedRate = 60000) // раз в минуту
    public void checkFatigueLevels() {
        for (Long athleteId : predService.findAllAthleteIds()) {
            FatiguePrediction latest = predService.getLatestForAthlete(athleteId);
            if (latest.getFatigueIndex() >= ALERT_THRESHOLD && !predService.wasAlerted(athleteId, latest)) {
                String message = String.format("Высокий уровень усталости: %.2f", latest.getFatigueIndex());
                notifyService.create(NotificationDto.builder()
                        .athleteId(athleteId)
                        .type(NotificationType.ALERT)
                        .message(message)
                        .build());
                predService.markAlertSent(athleteId, latest);
            }
        }
    }
}
