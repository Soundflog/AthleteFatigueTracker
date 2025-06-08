package com.example.athletefatiguetracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "fatigue_threshold")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FatigueThreshold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "threshold_id")
    private Long thresholdId;

    @Column(name = "category", length = 20, unique = true, nullable = false)
    private String category;        // Например: NORMAL, MODERATE, SEVERE

    @Column(name = "min_value", nullable = false)
    private Double minValue;        // Нижняя граница (включительно)

    @Column(name = "max_value", nullable = false)
    private Double maxValue;        // Верхняя граница (исключительно)

    @Column(name = "color_code", length = 7, nullable = false)
    private String colorCode;       // HEX-цвет для UI (например, "#00FF00")

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
