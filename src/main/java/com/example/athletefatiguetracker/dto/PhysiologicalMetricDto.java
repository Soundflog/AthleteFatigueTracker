package com.example.athletefatiguetracker.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PhysiologicalMetricDto {

    @NotNull(message = "Дата и время записи обязательны")
    private LocalDateTime recordedAt;

    @NotNull(message = "Частота сердечных сокращений обязательна")
    @Min(value = 30, message = "ЧСС должно быть ≥ 30")
    @Max(value = 220, message = "ЧСС должно быть ≤ 220")
    private Integer heartRateBpm;

    @NotNull(message = "Температура тела обязательна")
    @DecimalMin(value = "30.0", message = "Температура должна быть ≥ 30.0°C")
    @DecimalMax(value = "45.0", message = "Температура должна быть ≤ 45.0°C")
    private Double bodyTempC;

    @NotNull(message = "HRV обязательен")
    @Min(value = 0, message = "HRV не может быть отрицательным")
    private Integer hrvMs;

    @NotNull(message = "SpO₂ обязательен")
    @DecimalMin(value = "50.0", message = "SpO₂ должно быть ≥ 50%")
    @DecimalMax(value = "100.0", message = "SpO₂ должно быть ≤ 100%")
    private Double spo2Percent;

    @NotNull(message = "Уровень гидратации обязателен")
    @DecimalMin(value = "0.0", message = "Уровень гидратации должен быть ≥ 0.0")
    @DecimalMax(value = "1.0", message = "Уровень гидратации должен быть ≤ 1.0")
    private Double hydrationLevel;

    @NotNull(message = "Субъективный уровень усталости обязателен")
    @Min(value = 1, message = "Минимальное значение усталости = 1")
    @Max(value = 10, message = "Максимальное значение усталости = 10")
    private Integer subjectiveFatigue;
}
