package com.example.athletefatiguetracker.observer;

import com.example.athletefatiguetracker.entity.FatiguePrediction;
import com.example.athletefatiguetracker.notification.EmailNotificationTemplate;
import com.example.athletefatiguetracker.notification.PushNotificationTemplate;
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