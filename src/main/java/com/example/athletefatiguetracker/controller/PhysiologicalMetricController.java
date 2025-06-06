package com.example.athletefatiguetracker.controller;

import com.example.athletefatiguetracker.dto.PhysiologicalMetricDto;
import com.example.athletefatiguetracker.entity.PhysiologicalMetric;
import com.example.athletefatiguetracker.service.PhysiologicalMetricService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/metrics")
@RequiredArgsConstructor
public class PhysiologicalMetricController {

    private final PhysiologicalMetricService metricService;

    /**
     * Сохранение физиологических данных (POST).
     * @param dto DTO с физиологическими показателями
     * @param authentication Spring Security Authentication (в нём хранится userId после JWT-фильтра)
     * @return 201 Created + ID новой записи
     */
    @Operation(
            summary = "Сохранить физиологические данные",
            description = "Сохраняет данные спортсмена и возвращает ID новой записи",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешно создано",
                            content = @Content(schema = @Schema(implementation = Long.class))),
                    @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
            }
    )
    @PostMapping
    public ResponseEntity<?> saveMetric(@Validated @RequestBody PhysiologicalMetricDto dto,
                                        Authentication authentication) {
        // Предполагаем, что userId хранится в обязательном атрибуте principal (Long)
        Long athleteId = Long.valueOf(authentication.getName());
        PhysiologicalMetric saved = metricService.saveMetric(athleteId, dto);
        return ResponseEntity
                .created(URI.create("/api/v1/metrics/" + saved.getMetricId()))
                .body("{\"metricId\": " + saved.getMetricId() + "}");
    }

    /**
     * Получение всех физиологических данных текущего спортсмена (GET).
     */
    @Operation(
            summary = "Получить все записи физиологических данных",
            description = "Возвращает список всех сохранённых показателей для текущего спортсмена",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список показателей",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PhysiologicalMetric.class))))
            }
    )
    @GetMapping
    public ResponseEntity<List<PhysiologicalMetric>> getMetricsForCurrentUser(Authentication authentication) {
        Long athleteId = Long.valueOf(authentication.getName());
        List<PhysiologicalMetric> metrics = metricService.getMetricsForAthlete(athleteId);
        return ResponseEntity.ok(metrics);
    }
}
