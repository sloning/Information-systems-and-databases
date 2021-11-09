package com.isbd.Dao;

import com.isbd.model.Effect;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EffectDaoImpl implements Dao<Effect> {
    private final RowMapper<Effect> rowMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Effect> get(long id) {
        String sql = "select * from effect where effect_id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
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
}
