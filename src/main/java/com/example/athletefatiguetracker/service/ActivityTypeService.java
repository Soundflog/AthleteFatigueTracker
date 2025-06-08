package com.example.athletefatiguetracker.service;

import com.example.athletefatiguetracker.dto.ActivityTypeDto;
import com.example.athletefatiguetracker.entity.ActivityType;
import com.example.athletefatiguetracker.exception.ResourceNotFoundException;
import com.example.athletefatiguetracker.repository.ActivityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityTypeService {

    private final ActivityTypeRepository repository;

    public List<ActivityType> getAll() {
        return repository.findAll();
    }

    public ActivityType getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ActivityType with id " + id + " not found"));
    }

    public ActivityType getByCode(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("ActivityType with code " + code + " not found"));
    }

    @Transactional
    public ActivityType create(ActivityTypeDto dto) {
        ActivityType entity = ActivityType.builder()
                .code(dto.getCode())
                .description(dto.getDescription())
                .build();
        return repository.save(entity);
    }

    @Transactional
    public ActivityType update(Long id, ActivityTypeDto dto) {
        ActivityType existing = getById(id);
        existing.setCode(dto.getCode());
        existing.setDescription(dto.getDescription());
        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        ActivityType existing = getById(id);
        repository.delete(existing);
    }
}

