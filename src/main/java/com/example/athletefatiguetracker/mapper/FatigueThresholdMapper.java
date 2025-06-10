package com.example.athletefatiguetracker.mapper;

import com.example.athletefatiguetracker.dto.FatigueThresholdDto;
import com.example.athletefatiguetracker.entity.FatigueThreshold;
import org.springframework.stereotype.Component;

@Component
public class FatigueThresholdMapper {

    public FatigueThreshold toEntity(FatigueThresholdDto dto) {
        if (dto == null) {
            return null;
        }
        return FatigueThreshold.builder()
                .category(dto.getCategory())
                .minValue(dto.getMinValue())
                .maxValue(dto.getMaxValue())
                .colorCode(dto.getColorCode())
                .build();
    }

}
