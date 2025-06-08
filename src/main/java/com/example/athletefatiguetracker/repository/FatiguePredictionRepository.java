package com.example.athletefatiguetracker.repository;

import com.example.athletefatiguetracker.entity.FatiguePrediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FatiguePredictionRepository extends JpaRepository<FatiguePrediction, Long> {
    List<FatiguePrediction> findByAthleteIdOrderByPredictionTimestampDesc(Long athleteId);
}
