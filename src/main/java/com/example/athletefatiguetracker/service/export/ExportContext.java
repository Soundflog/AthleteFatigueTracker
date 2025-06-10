package com.example.athletefatiguetracker.service.export;

import com.example.athletefatiguetracker.dto.ExportRequest;
import com.example.athletefatiguetracker.service.inter.IExportStrategy;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExportContext {
    private final List<IExportStrategy> strategies;
    public ExportContext(List<IExportStrategy> strategies) {
        this.strategies = strategies;
    }
    public void export(ExportRequest req, HttpServletResponse resp) throws IOException {
        strategies.stream()
                .filter(s -> s.supports(req.getType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown export type"))
                .export(req, resp);
    }
}