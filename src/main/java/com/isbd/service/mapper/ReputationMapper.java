package com.isbd.service.mapper;

import com.isbd.dto.ReputationDto;
import com.isbd.model.ReputationLevel;
import com.isbd.model.TradingReputation;
import org.springframework.stereotype.Component;

@Component
public class ReputationMapper {
    public ReputationDto createFrom(TradingReputation tradingReputation, ReputationLevel reputationLevel) {
        ReputationDto dto = new ReputationDto();

        dto.setId(reputationLevel.getId());
        dto.setName(reputationLevel.getName());
        dto.setReputation(tradingReputation.getReputation());

        return dto;
    }
}
