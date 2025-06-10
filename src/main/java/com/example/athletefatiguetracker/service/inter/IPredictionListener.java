package com.example.athletefatiguetracker.service.inter;

import com.example.athletefatiguetracker.entity.FatiguePrediction;

public interface IPredictionListener {
    void onNewPrediction(FatiguePrediction pred);
}
