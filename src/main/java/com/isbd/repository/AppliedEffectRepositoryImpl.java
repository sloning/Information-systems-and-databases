package com.isbd.repository;

import com.isbd.model.AppliedEffect;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AppliedEffectRepositoryImpl implements AppliedEffectRepository {
    private final RowMapper<AppliedEffect> rowMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<AppliedEffect> getByPlayer(long playerId) {
        String sql = "select * from applied_effect where player_id = ?";

        return jdbcTemplate.query(sql, rowMapper, playerId);
    }

    @Override
    public int save(AppliedEffect appliedEffect) {
        String sql = "insert into applied_effect values(?, ?, ?, ?)" +
                "on conflict(player_id, effect_id) do update set end_time = ?";

        return jdbcTemplate.update(sql, appliedEffect.getId(), appliedEffect.getPlayerId(), appliedEffect.getStartTime(),
                appliedEffect.getEndTime(), appliedEffect.getEndTime());
    }

    @Override
    public int deleteEndedEffectsOfPlayer(long playerId) {
        String sql = "delete from applied_effect where player_id = ? and end_time <= current_timestamp";

        return jdbcTemplate.update(sql, playerId);
    }
}
