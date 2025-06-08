package com.example.athletefatiguetracker.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FatigueThresholdDto {

    @NotBlank(message = "Категория обязательна")
    @Size(max = 20, message = "Категория не должна превышать 20 символов")
    private String category;

    @NotNull(message = "Минимальное значение обязательно")
    @DecimalMin(value = "0.0", message = "Минимальное значение ≥ 0")
    private Double minValue;

    @NotNull(message = "Максимальное значение обязательно")
    @DecimalMin(value = "0.0", message = "Максимальное значение ≥ 0")
    private Double maxValue;

    @NotBlank(message = "Цветовой код обязателен")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "Цвет должен быть в формате HEX, напр. #FF0000")
    private String colorCode;
}
