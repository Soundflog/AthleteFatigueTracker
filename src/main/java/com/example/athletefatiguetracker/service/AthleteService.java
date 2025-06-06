package com.example.athletefatiguetracker.service;

import com.example.athletefatiguetracker.dto.AthleteDto;
import com.example.athletefatiguetracker.dto.UpdateAthleteDto;
import com.example.athletefatiguetracker.entity.Athlete;
import com.example.athletefatiguetracker.exception.ResourceNotFoundException;
import com.example.athletefatiguetracker.mapper.AthleteMapper;
import com.example.athletefatiguetracker.repository.AthleteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AthleteService {

    private final AthleteRepository athleteRepository;
    private final AthleteMapper athleteMapper;

    /**
     * Создание нового профиля спортсмена.
     * Вернёт сохранённую сущность с заполненным ID.
     */
    @Transactional
    public Athlete createAthlete(Long userId, AthleteDto dto) {
        Athlete athlete = athleteMapper.toEntity(dto);
        // Можно проверить, не существует ли уже профиль для userId
        // e.g. athleteRepository.existsByUserId(userId) — если поле userId в сущности
        // но здесь предполагаем привязку userId неявно
        return athleteRepository.save(athlete);
    }

    /**
     * Получить профиль по его ID.
     */
    public Athlete getAthleteById(Long athleteId) {
        return athleteRepository.findById(athleteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Athlete with id " + athleteId + " not found"));
    }

    /**
     * Обновление профиля спортсмена.
     */
    @Transactional
    public Athlete updateAthlete(Long athleteId, UpdateAthleteDto dto) {
        Athlete existing = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Athlete with id " + athleteId + " not found"));
        athleteMapper.updateEntity(existing, dto);
        return athleteRepository.save(existing);
    }

    /**
     * Удаление профиля спортсмена (если потребуется).
     */
    @Transactional
    public void deleteAthlete(Long athleteId) {
        Athlete existing = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Athlete with id " + athleteId + " not found"));
        athleteRepository.delete(existing);
    }
}

