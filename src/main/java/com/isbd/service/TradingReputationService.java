package com.isbd.service;

import com.isbd.exception.EntityNotSavedException;
import com.isbd.model.TradingReputation;
import com.isbd.repository.TradingReputationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TradingReputationService {
    private final TradingReputationRepository tradingReputationRepository;

    public TradingReputation getTradingReputationByPlayerIdAndVillagerId(long playerId, int villagerId) {
        Optional<TradingReputation> optionalTradingReputation =
                tradingReputationRepository.getByPlayerAndVillager(playerId, villagerId);

        if (optionalTradingReputation.isEmpty()) {
            TradingReputation newTradingReputation = new TradingReputation();
            newTradingReputation.setReputation(0);
            newTradingReputation.setPlayerId(playerId);
            newTradingReputation.setVillagerId(villagerId);
            save(newTradingReputation);
            return newTradingReputation;
        } else {
            return optionalTradingReputation.get();
        }
    }

    public void save(TradingReputation tradingReputation) {
        if (tradingReputationRepository.save(tradingReputation) == 0) {
            throw new EntityNotSavedException(String.format("Репутация игрока %d у жителя %d не сохранена",
                    tradingReputation.getPlayerId(), tradingReputation.getVillagerId()));
        }
    }
}
