package com.example.athletefatiguetracker.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAthleteDto {

    @Size(max = 50, message = "Имя не должно превышать 50 символов")
    private String firstName;

    @Size(max = 50, message = "Фамилия не должна превышать 50 символов")
    private String lastName;

    private LocalDate birthDate;

    @Pattern(regexp = "M|F", message = "Пол должен быть 'M' или 'F'")
    private String gender;

    @DecimalMin(value = "50.0", message = "Рост должен быть ≥ 50 см")
    @DecimalMax(value = "300.0", message = "Рост должен быть ≤ 300 см")
    private Double heightCm;

    @DecimalMin(value = "20.0", message = "Вес должен быть ≥ 20 кг")
    @DecimalMax(value = "500.0", message = "Вес должен быть ≤ 500 кг")
    private Double weightKg;

    @Size(max = 20, message = "Уровень подготовки не должен превышать 20 символов")
    private String trainingLevel;
}
