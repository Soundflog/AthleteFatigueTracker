package com.example.athletefatiguetracker.service;

import com.example.athletefatiguetracker.entity.FatiguePrediction;
import com.example.athletefatiguetracker.entity.PhysiologicalMetric;
import com.example.athletefatiguetracker.exception.ResourceNotFoundException;
import com.example.athletefatiguetracker.repository.FatiguePredictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FatiguePredictionService {

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

    /**
     * Возвращает идентификаторы всех спортсменов, у которых есть хотя бы один прогноз.
     */
    public List<Long> findAllAthleteIds() {
        return predictionRepository.findAll().stream()
                .map(FatiguePrediction::getAthleteId)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Возвращает самый последний прогноз для данного спортсмена.
     * @throws ResourceNotFoundException если нет ни одного прогноза
     */
    public FatiguePrediction getLatestForAthlete(Long athleteId) {
        return predictionRepository.findByAthleteIdOrderByPredictionTimestampDesc(athleteId).stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No predictions found for athlete " + athleteId));
    }

    /**
     * Проверяет, отправлено ли уведомление по этой записи.
     * @param athleteId  ID спортсмена
     * @param prediction Последний прогноз
     */
    public boolean wasAlerted(Long athleteId, FatiguePrediction prediction) {
        // Можно дополнительно проверять, что prediction.athleteId == athleteId
        return prediction.isAlertSent();
    }

    /**
     * Отмечает, что по данной записи уже отправлено уведомление.
     */
    @Transactional
    public void markAlertSent(Long athleteId, FatiguePrediction prediction) {
        FatiguePrediction entity = predictionRepository.findById(prediction.getPredictionId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Prediction not found: " + prediction.getPredictionId()));
        entity.setAlertSent(true);
        predictionRepository.save(entity);
    }

    /**
     * Поиск всех прогнозов спортсмена (опционально).
     */
    public List<FatiguePrediction> getAllForAthlete(Long athleteId) {
        return predictionRepository.findByAthleteIdOrderByPredictionTimestampDesc(athleteId);
    }

}
