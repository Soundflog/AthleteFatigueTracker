package com.example.athletefatiguetracker.service.export;

import com.example.athletefatiguetracker.dto.ExportRequest;
import com.example.athletefatiguetracker.entity.FatiguePrediction;
import com.example.athletefatiguetracker.entity.PhysiologicalMetric;
import com.example.athletefatiguetracker.repository.FatiguePredictionRepository;
import com.example.athletefatiguetracker.repository.PhysiologicalMetricRepository;
import com.example.athletefatiguetracker.service.inter.IExportStrategy;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;


@Component
@RequiredArgsConstructor
public class CsvExportStrategy implements IExportStrategy {

    private final PhysiologicalMetricRepository metricRepo;
    private final FatiguePredictionRepository predRepo;

    @Override
    public boolean supports(String type) {
        return "CSV".equalsIgnoreCase(type);
    }

    private String[] getHeaders(String data) {
        return "METRICS".equalsIgnoreCase(data)
                ? new String[]{"RecordedAt", "HR (bpm)", "Temp (°C)", "HRV (ms)", "SpO2 (%)", "Hydration", "Fatigue"}
                : new String[]{"Timestamp", "FatigueIndex", "Category"};
    }

    @Override
    public void export(ExportRequest req, HttpServletResponse resp) throws IOException {
        LocalDateTime start = req.getFrom().atStartOfDay();
        LocalDateTime end = req.getTo().plusDays(1).atStartOfDay();

        resp.setContentType("text/csv");
        resp.setHeader("Content-Disposition",
                "attachment; filename=\"" + req.getData().toLowerCase() + ".csv\"");
        try (CSVPrinter csv = new CSVPrinter(resp.getWriter(),
                CSVFormat.DEFAULT.withHeader(getHeaders(req.getData())))) {
            if ("METRICS".equalsIgnoreCase(req.getData())) {
                List<PhysiologicalMetric> rows =
                        metricRepo.findByAthleteIdAndRecordedAtBetween(req.getAthleteId(), start, end);
                for (var m : rows) {
                    csv.printRecord(
                            m.getRecordedAt(),
                            m.getHeartRateBpm(),
                            m.getBodyTempC(),
                            m.getHrvMs(),
                            m.getSpo2Percent(),
                            m.getHydrationLevel(),
                            m.getSubjectiveFatigue()
                    );
                }
            } else {
                List<FatiguePrediction> rows =
                        predRepo.findByAthleteIdAndPredictionTimestampBetween(req.getAthleteId(), start, end);
                for (var p : rows) {
                    csv.printRecord(
                            p.getPredictionTimestamp(),
                            p.getFatigueIndex(),
                            p.getFatigueCategory()
                    );
                }
            }
        }
    }
}