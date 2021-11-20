package com.isbd.repository;

import com.isbd.model.Effect;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EffectRepositoryImpl implements EffectRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Effect> get(long id) {
        String sql = "select * from effect where effect_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToEffect), id);
    }

    @Override
    public List<Effect> getAll() {
        return null;
    }

    @Override
    public int save(Effect effect) {
        return 0;
    }

    @Override
    public int update(Effect effect) {
        return 0;
    }

    @Override
    public int delete(Effect effect) {
        return 0;
    }

    public Effect mapRowToEffect(ResultSet rs, int rowNum) throws SQLException {
        Effect effect = new Effect();
        effect.setId(rs.getInt("effect_id"));
        effect.setName(rs.getString("name"));
        effect.setPower(rs.getInt("power"));
        effect.setDuration(rs.getInt("duration"));
        return effect;
    }
}
