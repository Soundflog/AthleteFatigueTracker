package com.example.athletefatiguetracker.controller;

import com.example.athletefatiguetracker.dto.ExportRequest;
import com.example.athletefatiguetracker.service.ExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;

    @Operation(summary = "Экспорт данных в CSV или PDF")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void export(
            @Validated @RequestBody ExportRequest request,
            HttpServletResponse response) throws IOException {

        exportService.export(request, response);
    }
}
