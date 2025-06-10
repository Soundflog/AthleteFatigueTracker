package com.example.athletefatiguetracker.controller;

import com.example.athletefatiguetracker.dto.FatigueThresholdDto;
import com.example.athletefatiguetracker.entity.FatigueThreshold;
import com.example.athletefatiguetracker.service.inter.IFatigueThresholdService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fatigue-thresholds")
@RequiredArgsConstructor
public class FatigueThresholdController {

    private final IFatigueThresholdService service;

    @Operation(summary = "Получить все пороги усталости")
    @GetMapping
    public ResponseEntity<List<FatigueThreshold>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Получить порог усталости по ID")
    @GetMapping("/{id}")
    public ResponseEntity<FatigueThreshold> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Создать новый порог усталости")
    @PostMapping
    public ResponseEntity<FatigueThreshold> create(@Validated @RequestBody FatigueThresholdDto dto) {
        FatigueThreshold created = service.create(dto);
        return ResponseEntity
                .created(URI.create("/api/v1/fatigue-thresholds/" + created.getThresholdId()))
                .body(created);
    }

    @Operation(summary = "Обновить порог усталости")
    @PutMapping("/{id}")
    public ResponseEntity<FatigueThreshold> update(
            @PathVariable Long id,
            @Validated @RequestBody FatigueThresholdDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Удалить порог усталости")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

