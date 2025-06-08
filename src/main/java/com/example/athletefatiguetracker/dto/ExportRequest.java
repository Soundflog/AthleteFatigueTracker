package com.example.athletefatiguetracker.dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ExportRequest {
    private Long athleteId;         // ID спортсмена
    private LocalDate from;         // начало периода
    private LocalDate to;           // конец периода
    private String type;            // "CSV" или "PDF"
    private String data;            // "METRICS" или "PREDICTIONS"
}
