package com.example.athletefatiguetracker.service;

import com.example.athletefatiguetracker.dto.FatigueThresholdDto;
import com.example.athletefatiguetracker.entity.FatigueThreshold;
import com.example.athletefatiguetracker.exception.ResourceNotFoundException;
import com.example.athletefatiguetracker.mapper.FatigueThresholdMapper;
import com.example.athletefatiguetracker.repository.FatigueThresholdRepository;
import com.example.athletefatiguetracker.service.inter.IFatigueThresholdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FatigueThresholdService implements IFatigueThresholdService {

    private final FatigueThresholdRepository repository;
    private final FatigueThresholdMapper mapper;

    @Override
    public List<FatigueThreshold> getAll() {
        return repository.findAll();
    }

    @Override
    public FatigueThreshold getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FatigueThreshold with id " + id + " not found"));
    }

    @Override
    public FatigueThreshold getByCategory(String category) {
        return repository.findByCategory(category)
                .orElseThrow(() -> new ResourceNotFoundException("FatigueThreshold with category " + category + " not found"));
    }

    @Override
    @Transactional
    public FatigueThreshold create(FatigueThresholdDto dto) {
        return repository.save(
                mapper.toEntity(dto)
        );
    }

    @Override
    @Transactional
    public FatigueThreshold update(Long id, FatigueThresholdDto dto) {
        FatigueThreshold existing = getById(id);
        existing.setCategory(dto.getCategory());
        existing.setMinValue(dto.getMinValue());
        existing.setMaxValue(dto.getMaxValue());
        existing.setColorCode(dto.getColorCode());
        return repository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        FatigueThreshold existing = getById(id);
        repository.delete(existing);
    }
}
