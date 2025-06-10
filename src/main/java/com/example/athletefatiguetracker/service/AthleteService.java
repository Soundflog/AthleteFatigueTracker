package com.example.athletefatiguetracker.service;

import com.example.athletefatiguetracker.dto.AthleteDto;
import com.example.athletefatiguetracker.dto.UpdateAthleteDto;
import com.example.athletefatiguetracker.entity.Athlete;
import com.example.athletefatiguetracker.exception.ResourceNotFoundException;
import com.example.athletefatiguetracker.mapper.AthleteMapper;
import com.example.athletefatiguetracker.repository.AthleteRepository;
import com.example.athletefatiguetracker.service.inter.IAthleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AthleteService implements IAthleteService {

    private final AthleteRepository athleteRepository;
    private final AthleteMapper athleteMapper;

    /**
     * Создание нового профиля спортсмена.
     * Вернёт сохранённую сущность с заполненным ID.
     */
    @Override
    @Transactional
    public Athlete createAthlete(AthleteDto dto) {
        Athlete athlete = athleteMapper.toEntity(dto);
        // Можно проверить, не существует ли уже профиль для userId
        // e.g. athleteRepository.existsByUserId(userId) — если поле userId в сущности
        // но здесь предполагаем привязку userId неявно
        return athleteRepository.save(athlete);
    }

    /**
     * Получить профиль по его ID.
     */
    @Override
    public Athlete getAthleteById(Long athleteId) {
        return athleteRepository.findById(athleteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Athlete with id " + athleteId + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Athlete> listAthletes() {
        return athleteRepository.findAll();
    }

    /**
     * Обновление профиля спортсмена.
     */
    @Override
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
    @Override
    @Transactional
    public void deleteAthlete(Long athleteId) {
        Athlete existing = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Athlete with id " + athleteId + " not found"));
        athleteRepository.delete(existing);
    }
}

