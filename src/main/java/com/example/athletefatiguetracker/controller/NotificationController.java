package com.example.athletefatiguetracker.controller;

import com.example.athletefatiguetracker.dto.NotificationDto;
import com.example.athletefatiguetracker.dto.NotificationResponseDto;
import com.example.athletefatiguetracker.service.inter.INotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final INotificationService service;

    @Operation(summary = "Создать уведомление")
    @PostMapping
    public ResponseEntity<NotificationResponseDto> create(
            @Validated @RequestBody NotificationDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Получить все уведомления спортсмена")
    @GetMapping("/athlete/{athleteId}")
    public ResponseEntity<List<NotificationResponseDto>> getForAthlete(
            @PathVariable Long athleteId) {
        return ResponseEntity.ok(service.getForAthlete(athleteId));
    }

    @Operation(summary = "Отметить уведомление как прочитанное")
    @PostMapping("/{id}/read")
    public ResponseEntity<Void> markRead(@PathVariable("id") Long id) {
        service.markAsRead(id);
        return ResponseEntity.noContent().build();
    }
}

