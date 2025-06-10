package com.example.athletefatiguetracker.service.inter;

import com.example.athletefatiguetracker.dto.FatigueThresholdDto;
import com.example.athletefatiguetracker.entity.FatigueThreshold;

import java.util.List;

public interface IFatigueThresholdService {
    List<FatigueThreshold> getAll();

    FatigueThreshold getById(Long id);

    FatigueThreshold getByCategory(String category);

    FatigueThreshold create(FatigueThresholdDto dto);

    FatigueThreshold update(Long id, FatigueThresholdDto dto);

    void delete(Long id);
}
