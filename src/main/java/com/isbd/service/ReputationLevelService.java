package com.isbd.service;

import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.ReputationLevel;
import com.isbd.model.TradingReputation;
import com.isbd.repository.ReputationLevelRepository;
import com.isbd.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReputationLevelService {
    private final ReputationLevelRepository reputationLevelRepository;
    private final TradingReputationService tradingReputationService;
    private final AuthenticationFacade authenticationFacade;

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
}
