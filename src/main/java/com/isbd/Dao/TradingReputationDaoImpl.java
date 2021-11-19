package com.isbd.Dao;

import com.isbd.model.TradingReputation;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TradingReputationDaoImpl implements TradingReputationDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<TradingReputation> rowMapper;

    @Override
    public Optional<TradingReputation> getByPlayerAndVillager(long playerId, int villagerId) {
        String sql = "select * from trading_reputation where player_id = ? and villager_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(rowMapper), playerId, villagerId);
    }

    @Override
    public int save(TradingReputation tradingReputation) {
        String sql = "insert into trading_reputation values(?, ?, ?)";

        return jdbcTemplate.update(sql, tradingReputation.getPlayerId(), tradingReputation.getVillagerId(),
                tradingReputation.getReputation());
    }
}
