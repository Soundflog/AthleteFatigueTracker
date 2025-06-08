package com.example.athletefatiguetracker.service;

import com.example.athletefatiguetracker.entity.PhysiologicalMetric;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FatigueAnalysisClient {

    private final WebClient mlWebClient;

    public Mono<FatigueResponse> predict(PhysiologicalMetric metric) {
        // сериализуем только поля, которые ждёт ML-сервис
        return mlWebClient.post()
                .uri("/ml/predict")
                .bodyValue(new FatigueRequest(
                        metric.getHeartRateBpm(),
                        metric.getBodyTempC(),
                        metric.getHrvMs(),
                        metric.getSpo2Percent(),
                        metric.getHydrationLevel(),
                        metric.getSubjectiveFatigue()
                ))
                .retrieve()
                .bodyToMono(FatigueResponse.class);
    }

    // Внутренний DTO запроса
    @lombok.Value
    public static class FatigueRequest {
        int heartRateBpm;
        double bodyTempC;
        int hrvMs;
        double spo2Percent;
        double hydrationLevel;
        int subjectiveFatigue;
    }

    // Внутренний DTO ответа
    @lombok.Data
    public static class FatigueResponse {
        private double fatigueIndex;
        private String category;
    }
}
