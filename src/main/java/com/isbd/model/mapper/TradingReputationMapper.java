package com.isbd.model.mapper;

import com.isbd.model.TradingReputation;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TradingReputationMapper implements RowMapper<TradingReputation> {
    @Override
    public TradingReputation mapRow(ResultSet rs, int rowNum) throws SQLException {
        TradingReputation tradingReputation = new TradingReputation();
        tradingReputation.setPlayerId(rs.getLong("player_id"));
        tradingReputation.setVillagerId(rs.getInt("villager_id"));
        tradingReputation.setReputation(rs.getInt("reputation_level"));
        return tradingReputation;
    }
}
