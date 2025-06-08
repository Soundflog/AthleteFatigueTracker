package com.example.athletefatiguetracker.repository;

import com.example.athletefatiguetracker.entity.FatigueThreshold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FatigueThresholdRepository extends JpaRepository<FatigueThreshold, Long> {
    Optional<FatigueThreshold> findByCategory(String category);
}

