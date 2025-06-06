package com.example.athletefatiguetracker.controller;

import com.example.athletefatiguetracker.dto.AthleteDto;
import com.example.athletefatiguetracker.dto.UpdateAthleteDto;
import com.example.athletefatiguetracker.entity.Athlete;
import com.example.athletefatiguetracker.service.AthleteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/athlete")
@RequiredArgsConstructor
public class AthleteController {

    private final AthleteService athleteService;

    /**
     * Создать новый профиль спортсмена.
     * Пример: POST /api/v1/athlete
     */
    @Operation(summary = "Создать профиль спортсмена")
    @ApiResponse(responseCode = "201", description = "Профиль успешно создан")
    @PostMapping
    public ResponseEntity<?> createAthlete(
            @Validated @RequestBody AthleteDto dto,
            Authentication authentication) {

        // Если требуется связать профили: Long userId = Long.valueOf(authentication.getName());
        Athlete created = athleteService.createAthlete(null, dto);
        return ResponseEntity
                .created(URI.create("/api/v1/athlete/" + created.getAthleteId()))
                .body(created);
    }

    /**
     * Получить профиль спортсмена по ID.
     * Пример: GET /api/v1/athlete/{id}
     */
    @Operation(summary = "Получить профиль спортсмена")
    @ApiResponse(responseCode = "200", description = "Профиль найден и возвращён")
    @GetMapping("/{id}")
    public ResponseEntity<Athlete> getAthleteById(@PathVariable("id") Long athleteId) {
        Athlete athlete = athleteService.getAthleteById(athleteId);
        return ResponseEntity.ok(athlete);
    }

    /**
     * Обновить профиль спортсмена.
     * Пример: PUT /api/v1/athlete/{id}
     */
    @Operation(summary = "Обновить профиль спортсмена")
    @ApiResponse(responseCode = "200", description = "Профиль успешно обновлён")
    @PutMapping("/{id}")
    public ResponseEntity<Athlete> updateAthlete(
            @PathVariable("id") Long athleteId,
            @Validated @RequestBody UpdateAthleteDto dto) {

        Athlete updated = athleteService.updateAthlete(athleteId, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Удалить профиль спортсмена.
     * Пример: DELETE /api/v1/athlete/{id}
     */
    @Operation(summary = "Удалить профиль спортсмена")
    @ApiResponse(responseCode = "204", description = "Профиль успешно удалён")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAthlete(@PathVariable("id") Long athleteId) {
        athleteService.deleteAthlete(athleteId);
        return ResponseEntity.noContent().build();
    }
}

