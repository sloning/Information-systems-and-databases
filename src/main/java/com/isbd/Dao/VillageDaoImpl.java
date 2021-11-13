package com.isbd.Dao;

import com.isbd.model.Village;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VillageDaoImpl implements Dao<Village> {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Village> rowMapper;

    @Override
    public Optional<Village> get(long id) {
        String sql = "select * from village where village_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(rowMapper), id);
    }

    @Override
    public List<Village> getAll() {
        String sql = "select * from village";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int save(Village village) {
        return jdbcTemplate.update("insert into village(name, x_coordinate, z_coordinate) values(?, ?, ?)",
                village.getName(), village.getXCoordinate(), village.getZCoordinate());
    }

    @Override
    public int update(Village village) {
        return jdbcTemplate.
                update("update village set name = ?, x_coordinate = ?, z_coordinate = ? where village_id = ?",
                        village.getName(), village.getXCoordinate(), village.getZCoordinate(), village.getId());
    }

    @Override
    public int delete(Village village) {
        return jdbcTemplate.update("delete from village where village_id = ?", village.getId());
    }
}
