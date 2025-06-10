package com.example.athletefatiguetracker.service.inter;

import com.example.athletefatiguetracker.entity.FatiguePrediction;
import com.example.athletefatiguetracker.entity.PhysiologicalMetric;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IFatiguePredictionService {

    Mono<FatiguePrediction> analyzeAndSave(PhysiologicalMetric metric);

    List<Long> findAllAthleteIds();
    FatiguePrediction getLatestForAthlete(Long athleteId);

    boolean wasAlerted(Long athleteId, FatiguePrediction prediction);

    void markAlertSent(Long athleteId, FatiguePrediction prediction);


    List<FatiguePrediction> getAllForAthlete(Long athleteId);
}
