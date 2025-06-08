package com.example.athletefatiguetracker.service;

import com.example.athletefatiguetracker.entity.FatiguePrediction;
import com.example.athletefatiguetracker.entity.PhysiologicalMetric;
import com.example.athletefatiguetracker.repository.FatiguePredictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class FatigueService {

    private final FatigueAnalysisClient client;
    private final FatiguePredictionRepository predictionRepository;

    /**
     * Отправляет данные в ML-сервис, получает прогноз и сохраняет его.
     * Возвращает только что сохранённый прогноз.
     */
    public Mono<FatiguePrediction> analyzeAndSave(PhysiologicalMetric metric) {
        return client.predict(metric)
                .map(resp -> FatiguePrediction.builder()
                        .athleteId(metric.getAthleteId())
                        .fatigueIndex(resp.getFatigueIndex())
                        .fatigueCategory(resp.getCategory())
                        .build())
                .flatMap(p -> Mono.fromCallable(() -> predictionRepository.save(p))
                        .subscribeOn(Schedulers.boundedElastic())
                );
    }
}
