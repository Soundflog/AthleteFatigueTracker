package com.example.athletefatiguetracker.mapper;

import com.example.athletefatiguetracker.dto.AthleteDto;
import com.example.athletefatiguetracker.dto.UpdateAthleteDto;
import com.example.athletefatiguetracker.entity.Athlete;
import org.springframework.stereotype.Component;

@Component
public class AthleteMapper {

    public Athlete toEntity(AthleteDto dto) {
        if (dto == null) {
            return null;
        }
        return Athlete.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .heightCm(dto.getHeightCm())
                .weightKg(dto.getWeightKg())
                .trainingLevel(dto.getTrainingLevel())
                .build();
    }

    public void updateEntity(Athlete existing, UpdateAthleteDto dto) {
        if (dto.getFirstName() != null) {
            existing.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            existing.setLastName(dto.getLastName());
        }
        if (dto.getBirthDate() != null) {
            existing.setBirthDate(dto.getBirthDate());
        }
        if (dto.getGender() != null) {
            existing.setGender(dto.getGender());
        }
        if (dto.getHeightCm() != null) {
            existing.setHeightCm(dto.getHeightCm());
        }
        if (dto.getWeightKg() != null) {
            existing.setWeightKg(dto.getWeightKg());
        }
        if (dto.getTrainingLevel() != null) {
            existing.setTrainingLevel(dto.getTrainingLevel());
        }
    }
}

