package com.example.athletefatiguetracker.service.listener;

import com.example.athletefatiguetracker.entity.FatiguePrediction;
import com.example.athletefatiguetracker.service.inter.IPredictionListener;
import com.example.athletefatiguetracker.service.template.EmailNotificationTemplate;
import com.example.athletefatiguetracker.service.template.PushNotificationTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AlertNotificationListener implements IPredictionListener {

    private final EmailNotificationTemplate emailTemplate;
    private final PushNotificationTemplate pushTemplate;
    private static final double THRESHOLD = 0.8;

    @Override
    public void onNewPrediction(FatiguePrediction pred) {
        if (pred.getFatigueIndex() >= THRESHOLD) {
            String msg = String.format("Уровень усталости %.2f превышает порог", pred.getFatigueIndex());
            emailTemplate.notify(pred.getAthleteId(), msg);
            pushTemplate.notify(pred.getAthleteId(), msg);
        }
    }
}