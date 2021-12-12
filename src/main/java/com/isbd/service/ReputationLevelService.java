package com.isbd.service;

import com.isbd.dto.ReputationDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.ReputationLevel;
import com.isbd.model.TradingReputation;
import com.isbd.repository.ReputationLevelRepository;
import com.isbd.security.AuthenticationFacade;
import com.isbd.service.mapper.ReputationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReputationLevelService {
    private final ReputationLevelRepository reputationLevelRepository;
    private final TradingReputationService tradingReputationService;
    private final AuthenticationFacade authenticationFacade;
    private final ReputationMapper reputationMapper;

    public ReputationLevel getReputationLevelByTradingReputation(TradingReputation tradingReputation) {
        List<ReputationLevel> reputationLevels = reputationLevelRepository.getAll();
        if (reputationLevels.isEmpty()) {
            throw new EntityNotFoundException("Уровни репутации не существуют");
        }
        ReputationLevel reputationLevel = reputationLevels.get(0);
        for (ReputationLevel rl : reputationLevels) {
            if (tradingReputation.getReputation() >= rl.getNeededReputationLevel()) {
                reputationLevel = rl;
            }
        }
        return reputationLevel;
    }

    public ReputationLevel getReputationLevelByVillagerId(int villagerId) {
        long playerId = authenticationFacade.getPlayerId();
        TradingReputation tradingReputation = tradingReputationService.
                getTradingReputationByPlayerIdAndVillagerId(playerId, villagerId);
        return getReputationLevelByTradingReputation(tradingReputation);
    }

    public ReputationDto getReputationByVillagerId(int villagerId) {
        long playerId = authenticationFacade.getPlayerId();
        TradingReputation tradingReputation = tradingReputationService.
                getTradingReputationByPlayerIdAndVillagerId(playerId, villagerId);
        ReputationLevel reputationLevel = getReputationLevelByTradingReputation(tradingReputation);
        return reputationMapper.createFrom(tradingReputation, reputationLevel);
    }

    public List<ReputationLevel> getReputationLevels() {
        return reputationLevelRepository.getAll();
    }

    public ReputationLevel getReputationLevel(int reputationLevelId) {
        return reputationLevelRepository.get(reputationLevelId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Уровень репутации с идентификатором %d не найден",
                        reputationLevelId)));
    }

    public ReputationLevel getNextReputationLevelOrLast(int reputationLevelId) {
        return reputationLevelRepository.get(++reputationLevelId)
                .orElse(getReputationLevel(reputationLevelId));
    }
}
