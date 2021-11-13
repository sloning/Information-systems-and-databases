package com.isbd.model.mapper;

import com.isbd.model.Village;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class VillageMapper implements RowMapper<Village> {
    @Override
    public Village mapRow(ResultSet rs, int rowNum) throws SQLException {
        Village village = new Village();
        village.setId(rs.getInt("village_id"));
        village.setName(rs.getString("name"));
        village.setXCoordinate(rs.getInt("x_coordinate"));
        village.setZCoordinate(rs.getInt("z_coordinate"));
        return village;
    }
}
