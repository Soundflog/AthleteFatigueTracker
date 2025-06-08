package com.example.athletefatiguetracker.service;

import com.example.athletefatiguetracker.dto.PhysiologicalMetricDto;
import com.example.athletefatiguetracker.entity.PhysiologicalMetric;
import com.example.athletefatiguetracker.exception.ResourceNotFoundException;
import com.example.athletefatiguetracker.repository.PhysiologicalMetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhysiologicalMetricService {

    private final PhysiologicalMetricRepository metricRepository;
    private final FatigueService fatigueService;

    /**
     * Сохранение физиологических данных для текущего пользователя (athleteId берётся из Principal).
     */
    @Transactional
    public PhysiologicalMetric saveMetric(Long athleteId, PhysiologicalMetricDto dto) {
        PhysiologicalMetric entity = PhysiologicalMetric.builder()
                .athleteId(athleteId)
                .recordedAt(dto.getRecordedAt())
                .heartRateBpm(dto.getHeartRateBpm())
                .bodyTempC(dto.getBodyTempC())
                .hrvMs(dto.getHrvMs())
                .spo2Percent(dto.getSpo2Percent())
                .hydrationLevel(dto.getHydrationLevel())
                .subjectiveFatigue(dto.getSubjectiveFatigue())
                .build();

        PhysiologicalMetric saved = metricRepository.save(entity);

        // Асинхронно вызываем ML-сервис и сохраняем прогноз
        fatigueService.analyzeAndSave(saved)
                .subscribe(); // или .block() если нужен синхронный вызов

        return saved;
    }

    public List<PhysiologicalMetric> getMetricsForAthlete(Long athleteId) {
        if (athleteId == null || athleteId <= 0) {
            throw new ResourceNotFoundException("Некорректный идентификатор спортсмена");
        }
        return metricRepository.findByAthleteIdOrderByRecordedAtDesc(athleteId);
    }
}