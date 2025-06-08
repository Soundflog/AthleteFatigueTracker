package com.example.athletefatiguetracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityTypeDto {

    @NotBlank(message = "Код активности обязателен")
    @Size(max = 20, message = "Код не должен превышать 20 символов")
    private String code;

    @NotBlank(message = "Описание обязательно")
    @Size(max = 100, message = "Описание не должно превышать 100 символов")
    private String description;
}
