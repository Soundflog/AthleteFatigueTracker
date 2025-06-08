package com.example.athletefatiguetracker.service;

import com.example.athletefatiguetracker.dto.ExportRequest;
import com.example.athletefatiguetracker.entity.FatiguePrediction;
import com.example.athletefatiguetracker.entity.PhysiologicalMetric;
import com.example.athletefatiguetracker.repository.FatiguePredictionRepository;
import com.example.athletefatiguetracker.repository.PhysiologicalMetricRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.*;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;

@Service
@RequiredArgsConstructor
public class ExportService {

    private final PhysiologicalMetricRepository metricRepo;
    private final FatiguePredictionRepository predRepo;

    public void export(ExportRequest req, HttpServletResponse resp) throws IOException {
        LocalDateTime start = req.getFrom().atStartOfDay();
        LocalDateTime end = req.getTo().plusDays(1).atStartOfDay();

        if ("CSV".equalsIgnoreCase(req.getType())) {
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
        } else { // PDF
            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition",
                    "attachment; filename=\"" + req.getData().toLowerCase() + ".pdf\"");
            try (PdfWriter writer = new PdfWriter(resp.getOutputStream());
                 PdfDocument pdf = new PdfDocument(writer);
                 Document doc = new Document(pdf)) {

                // Заголовок
                doc.add(new Paragraph("Export: " + req.getData())
                        .setFontSize(16));

                // Таблица
                String[] headers = getHeaders(req.getData());
                Table table = new Table(headers.length);
                for (String h : headers) table.addHeaderCell(h);

                if ("METRICS".equalsIgnoreCase(req.getData())) {
                    List<PhysiologicalMetric> rows =
                            metricRepo.findByAthleteIdAndRecordedAtBetween(req.getAthleteId(), start, end);
                    for (var m : rows) {
                        table.addCell(m.getRecordedAt().toString());
                        table.addCell(String.valueOf(m.getHeartRateBpm()));
                        table.addCell(String.valueOf(m.getBodyTempC()));
                        table.addCell(String.valueOf(m.getHrvMs()));
                        table.addCell(String.valueOf(m.getSpo2Percent()));
                        table.addCell(String.valueOf(m.getHydrationLevel()));
                        table.addCell(String.valueOf(m.getSubjectiveFatigue()));
                    }
                } else {
                    List<FatiguePrediction> rows =
                            predRepo.findByAthleteIdAndPredictionTimestampBetween(req.getAthleteId(), start, end);
                    for (var p : rows) {
                        table.addCell(p.getPredictionTimestamp().toString());
                        table.addCell(String.valueOf(p.getFatigueIndex()));
                        table.addCell(p.getFatigueCategory());
                    }
                }
                doc.add(table);
            }
        }
    }

    private String[] getHeaders(String data) {
        return "METRICS".equalsIgnoreCase(data)
                ? new String[]{"RecordedAt", "HR (bpm)", "Temp (°C)", "HRV (ms)", "SpO2 (%)", "Hydration", "Fatigue"}
                : new String[]{"Timestamp", "FatigueIndex", "Category"};
    }
}
