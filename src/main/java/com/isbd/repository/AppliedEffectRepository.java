package com.isbd.repository;

import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.AppliedEffect;
import com.isbd.model.Effect;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AppliedEffectRepository {
    private final EffectRepository effectRepository;
    private final JdbcTemplate jdbcTemplate;

    public List<AppliedEffect> getByPlayer(long playerId) {
        String sql = "select * from applied_effect where player_id = ?";

        return jdbcTemplate.query(sql, this::mapRowToAppliedEffect, playerId);
    }

    public int save(AppliedEffect appliedEffect) {
        String sql = "insert into applied_effect values(?, ?, ?, ?)" +
                "on conflict(player_id, effect_id) do update set end_time = ?";

        return jdbcTemplate.update(sql, appliedEffect.getId(), appliedEffect.getPlayerId(), appliedEffect.getStartTime(),
                appliedEffect.getEndTime(), appliedEffect.getEndTime());
    }

    public int deleteEndedEffectsOfPlayer(long playerId) {
        String sql = "delete from applied_effect where player_id = ? and end_time <= current_timestamp";

        return jdbcTemplate.update(sql, playerId);
    }

    private AppliedEffect mapRowToAppliedEffect(ResultSet rs, int rowNum) throws SQLException {
        int effectId = rs.getInt("effect_id");
        Effect effect = effectRepository.get(effectId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Effect with id: %d was not found", effectId)));

        AppliedEffect appliedEffect = new AppliedEffect();
        appliedEffect.setPlayerId(rs.getLong("player_id"));
        appliedEffect.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
        appliedEffect.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
        appliedEffect.setId(effectId);
        appliedEffect.setName(effect.getName());
        appliedEffect.setDuration(effect.getDuration());
        appliedEffect.setPower(effect.getPower());
        return appliedEffect;
    }
}
