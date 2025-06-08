package com.example.athletefatiguetracker.repository;

import com.example.athletefatiguetracker.entity.PhysiologicalMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PhysiologicalMetricRepository extends JpaRepository<PhysiologicalMetric, Long> {

    List<PhysiologicalMetric> findByAthleteIdOrderByRecordedAtDesc(Long athleteId);

    List<PhysiologicalMetric> findByAthleteIdAndRecordedAtBetween(
            Long athleteId, LocalDateTime from, LocalDateTime to);

}