package com.example.athletefatiguetracker.controller;

import com.example.athletefatiguetracker.dto.ActivityTypeDto;
import com.example.athletefatiguetracker.entity.ActivityType;
import com.example.athletefatiguetracker.service.ActivityTypeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/activity-types")
@RequiredArgsConstructor
public class ActivityTypeController {

    private final ActivityTypeService service;

    @Operation(summary = "Получить все типы активности")
    @GetMapping
    public ResponseEntity<List<ActivityType>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Получить тип активности по ID")
    @GetMapping("/{id}")
    public ResponseEntity<ActivityType> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Создать новый тип активности")
    @PostMapping
    public ResponseEntity<ActivityType> create(@Validated @RequestBody ActivityTypeDto dto) {
        ActivityType created = service.create(dto);
        return ResponseEntity
                .created(URI.create("/api/v1/activity-types/" + created.getTypeId()))
                .body(created);
    }

    @Operation(summary = "Обновить тип активности")
    @PutMapping("/{id}")
    public ResponseEntity<ActivityType> update(
            @PathVariable Long id,
            @Validated @RequestBody ActivityTypeDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Удалить тип активности")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
