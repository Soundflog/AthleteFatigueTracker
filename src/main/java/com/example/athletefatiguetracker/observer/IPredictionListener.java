package com.example.athletefatiguetracker.observer;

import com.example.athletefatiguetracker.entity.FatiguePrediction;

public interface IPredictionListener {
    void onNewPrediction(FatiguePrediction pred);
}
