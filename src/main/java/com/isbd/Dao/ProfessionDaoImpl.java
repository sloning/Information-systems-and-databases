package com.isbd.Dao;

import com.isbd.model.Profession;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProfessionDaoImpl implements Dao<Profession> {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Profession> rowMapper;

    @Override
    public Optional<Profession> get(long id) {
        String sql = "select * from profession where profession_id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public List<Profession> getAll() {
        String sql = "select * from profession";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int save(Profession profession) {
        return jdbcTemplate.update("insert into profession(name) values(?)", profession.getName());
    }

    @Override
    public int update(Profession profession) {
        return jdbcTemplate.
                update("update profession set name = ? where profession_id = ?",
                        profession.getName(), profession.getId());
    }

    @Override
    public int delete(Profession profession) {
        return jdbcTemplate.update("delete from profession where profession_id = ?", profession.getId());
    }
}
