package com.isbd.repository;

import com.isbd.model.TradingReputation;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TradingReputationRepositoryImpl implements TradingReputationRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<TradingReputation> getByPlayerAndVillager(long playerId, int villagerId) {
        String sql = "select * from trading_reputation where player_id = ? and villager_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToTradingReputation),
                playerId, villagerId);
    }

    @Override
    public int save(TradingReputation tradingReputation) {
        String sql = "insert into trading_reputation values(?, ?, ?)";

        return jdbcTemplate.update(sql, tradingReputation.getPlayerId(), tradingReputation.getVillagerId(),
                tradingReputation.getReputation());
    }

    private TradingReputation mapRowToTradingReputation(ResultSet rs, int rowNum) throws SQLException {
        TradingReputation tradingReputation = new TradingReputation();
        tradingReputation.setPlayerId(rs.getLong("player_id"));
        tradingReputation.setVillagerId(rs.getInt("villager_id"));
        tradingReputation.setReputation(rs.getInt("reputation_level"));
        return tradingReputation;
    }
}
