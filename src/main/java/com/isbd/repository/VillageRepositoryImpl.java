package com.isbd.repository;

import com.isbd.model.Village;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VillageRepositoryImpl implements VillageRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Village> get(long id) {
        String sql = "select * from village where village_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToVillage), id);
    }

    @Override
    public List<Village> getAll(int limit, int offset) {
        String sql = "select * from village limit ? offset ?";

        return jdbcTemplate.query(sql, this::mapRowToVillage, limit, offset);
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

    public Village mapRowToVillage(ResultSet rs, int rowNum) throws SQLException {
        Village village = new Village();
        village.setId(rs.getInt("village_id"));
        village.setName(rs.getString("name"));
        village.setXCoordinate(rs.getInt("x_coordinate"));
        village.setZCoordinate(rs.getInt("z_coordinate"));
        return village;
    }
}
