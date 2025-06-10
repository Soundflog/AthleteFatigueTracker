package com.example.athletefatiguetracker.dto;

import com.example.athletefatiguetracker.entity.NotificationType;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NotificationDto {

    @NotNull
    private Long athleteId;

    @NotNull
    private NotificationType type;

    @NotBlank
    @Size(max = 255)
    private String message;
}

