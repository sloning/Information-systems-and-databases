package com.isbd.Dao.mapper;

import com.isbd.model.Effect;
import org.postgresql.util.PGInterval;
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
        effect.setDuration(new PGInterval(rs.getString("duration")));
        return effect;
    }
}
