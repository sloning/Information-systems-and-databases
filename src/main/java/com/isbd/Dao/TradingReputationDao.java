package com.isbd.Dao;

import com.isbd.model.TradingReputation;

import java.util.Optional;

public interface TradingReputationDao {
    Optional<TradingReputation> getByPlayerAndVillager(long playerId, int villagerId);

    int save(TradingReputation tradingReputation);
}
