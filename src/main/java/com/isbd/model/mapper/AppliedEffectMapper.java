package com.isbd.model.mapper;

import com.isbd.model.AppliedEffect;
import com.isbd.model.Effect;
import com.isbd.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class AppliedEffectMapper implements RowMapper<AppliedEffect> {
    private final Repository<Effect> effectRepository;

    @Override
    public AppliedEffect mapRow(ResultSet rs, int rowNum) throws SQLException {
        int effectId = rs.getInt("effect_id");
        Effect effect = effectRepository.get(effectId).get();

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
