package com.isbd.model.mapper;

import com.isbd.model.Effect;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EffectMapper implements RowMapper<Effect> {
    @Override
    public Effect mapRow(ResultSet rs, int rowNum) throws SQLException {
        Effect effect = new Effect();
        effect.setId(rs.getInt("effect_id"));
        effect.setName(rs.getString("name"));
        effect.setPower(rs.getInt("power"));
        effect.setDuration(rs.getInt("duration"));
        return effect;
    }
}
