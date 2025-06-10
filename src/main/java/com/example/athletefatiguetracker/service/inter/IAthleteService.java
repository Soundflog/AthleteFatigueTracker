package com.example.athletefatiguetracker.service.inter;

import com.example.athletefatiguetracker.dto.AthleteDto;
import com.example.athletefatiguetracker.dto.UpdateAthleteDto;
import com.example.athletefatiguetracker.entity.Athlete;
import java.util.List;

public interface IAthleteService {
    Athlete createAthlete(AthleteDto athlete);
    Athlete getAthleteById(Long athleteId);
    Athlete updateAthlete(Long id, UpdateAthleteDto athlete);
    List<Athlete> listAthletes();

    void deleteAthlete(Long athleteId);
}
