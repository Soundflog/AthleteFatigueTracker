package com.example.athletefatiguetracker.service;

import com.example.athletefatiguetracker.dto.FatigueThresholdDto;
import com.example.athletefatiguetracker.entity.FatigueThreshold;
import com.example.athletefatiguetracker.exception.ResourceNotFoundException;
import com.example.athletefatiguetracker.repository.FatigueThresholdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FatigueThresholdService {

    private final FatigueThresholdRepository repository;

    public List<FatigueThreshold> getAll() {
        return repository.findAll();
    }

    public FatigueThreshold getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FatigueThreshold with id " + id + " not found"));
    }

    public FatigueThreshold getByCategory(String category) {
        return repository.findByCategory(category)
                .orElseThrow(() -> new ResourceNotFoundException("FatigueThreshold with category " + category + " not found"));
    }

    @Transactional
    public FatigueThreshold create(FatigueThresholdDto dto) {
        FatigueThreshold entity = FatigueThreshold.builder()
                .category(dto.getCategory())
                .minValue(dto.getMinValue())
                .maxValue(dto.getMaxValue())
                .colorCode(dto.getColorCode())
                .build();
        return repository.save(entity);
    }

    @Transactional
    public FatigueThreshold update(Long id, FatigueThresholdDto dto) {
        FatigueThreshold existing = getById(id);
        existing.setCategory(dto.getCategory());
        existing.setMinValue(dto.getMinValue());
        existing.setMaxValue(dto.getMaxValue());
        existing.setColorCode(dto.getColorCode());
        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        FatigueThreshold existing = getById(id);
        repository.delete(existing);
    }
}
