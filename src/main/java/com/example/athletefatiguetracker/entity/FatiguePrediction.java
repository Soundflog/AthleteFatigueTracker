package com.example.athletefatiguetracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "fatigue_prediction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FatiguePrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prediction_id")
    private Long predictionId;

    @Column(name = "athlete_id", nullable = false)
    private Long athleteId;

    @Column(name = "prediction_timestamp", nullable = false)
    private LocalDateTime predictionTimestamp;

    @Column(name = "fatigue_index", nullable = false)
    private Double fatigueIndex;

    @Column(name = "fatigue_category", length = 20, nullable = false)
    private String fatigueCategory;

    @Column(name = "alert_sent", nullable = false)
    private boolean alertSent;    // флаг, что уведомление уже отправлено

    @PrePersist
    protected void onCreate() {
        predictionTimestamp = LocalDateTime.now();
        alertSent = false;
    }
}
