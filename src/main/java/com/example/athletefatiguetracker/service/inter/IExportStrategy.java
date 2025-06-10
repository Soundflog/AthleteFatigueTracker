package com.example.athletefatiguetracker.service.inter;

import com.example.athletefatiguetracker.dto.ExportRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IExportStrategy {
    boolean supports(String type);
    void export(ExportRequest req, HttpServletResponse resp) throws IOException;
}
