package com.example.athletefatiguetracker.repository;

import com.example.athletefatiguetracker.entity.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    // можно добавить методы поиска, если потребуются
}
