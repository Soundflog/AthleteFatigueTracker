package com.example.athletefatiguetracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "physiological_metric")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhysiologicalMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metric_id")
    private Long metricId;

    @Column(name = "athlete_id", nullable = false)
    private Long athleteId;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    @Column(name = "heart_rate_bpm", nullable = false)
    private Integer heartRateBpm;

    @Column(name = "body_temp_c", nullable = false)
    private Double bodyTempC;

    @Column(name = "hrv_ms", nullable = false)
    private Integer hrvMs;

    @Column(name = "spo2_percent", nullable = false)
    private Double spo2Percent;

    @Column(name = "hydration_level", nullable = false)
    private Double hydrationLevel;

    @Column(name = "subjective_fatigue", nullable = false)
    private Integer subjectiveFatigue;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}