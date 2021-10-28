package com.isbd.DAO;

import com.isbd.model.Village;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class VillageDAO implements DAO<Village> {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert =
            new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getDataSource())).withTableName("village");

    @Override
    public Optional<Village> get(long id) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("select * from village where village_id = ?", Village.class, id));
    }

    @Override
    public List<Village> getAll() {
        return jdbcTemplate.queryForList("select * from village", Village.class);
    }

    @Override
    public int save(Village village) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("village_id", village.getId());
        parameters.put("name", village.getName());
        parameters.put("x_coordinate", village.getXCoordinate());
        parameters.put("z_coordinate", village.getZCoordinate());

        return simpleJdbcInsert.execute(parameters);
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
