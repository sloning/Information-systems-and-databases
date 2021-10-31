package com.isbd.DAO.mapper;

import com.isbd.DAO.DAO;
import com.isbd.model.AppliedEffect;
import com.isbd.model.Effect;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class AppliedEffectMapper implements RowMapper<AppliedEffect> {
    private final DAO<Effect> effectDAO;

    @Override
    public AppliedEffect mapRow(ResultSet rs, int rowNum) throws SQLException {
        int effectId = rs.getInt("effect_id");
        Effect effect = effectDAO.get(effectId).get();

        AppliedEffect appliedEffect = new AppliedEffect();
        appliedEffect.setPlayerId(rs.getLong("player_id"));
        appliedEffect.setStartTime(rs.getDate("start_time"));
        appliedEffect.setEndTime(rs.getDate("end_time"));
        appliedEffect.setId(effectId);
        appliedEffect.setName(effect.getName());
        appliedEffect.setDuration(effect.getDuration());
        appliedEffect.setPower(effect.getPower());
        return appliedEffect;
    }
}
