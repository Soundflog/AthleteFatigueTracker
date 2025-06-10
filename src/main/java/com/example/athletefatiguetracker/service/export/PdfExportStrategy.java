package com.example.athletefatiguetracker.service.export;

import com.example.athletefatiguetracker.dto.ExportRequest;
import com.example.athletefatiguetracker.entity.FatiguePrediction;
import com.example.athletefatiguetracker.entity.PhysiologicalMetric;
import com.example.athletefatiguetracker.repository.FatiguePredictionRepository;
import com.example.athletefatiguetracker.repository.PhysiologicalMetricRepository;
import com.example.athletefatiguetracker.service.inter.IExportStrategy;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor
public class PdfExportStrategy implements IExportStrategy {
    private final PhysiologicalMetricRepository metricRepo;
    private final FatiguePredictionRepository predRepo;

    @Override
    public boolean supports(String type) {
        return "PDF".equalsIgnoreCase(type);
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
