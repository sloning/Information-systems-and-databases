package com.isbd.repository;

import com.isbd.model.TradingReputation;

import java.util.Optional;

public interface TradingReputationRepository {
    Optional<TradingReputation> getByPlayerAndVillager(long playerId, int villagerId);

    int save(TradingReputation tradingReputation);
}
