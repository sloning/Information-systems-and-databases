package com.isbd.model.mapper;

import com.isbd.model.Raid;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RaidMapper implements RowMapper<Raid> {
    @Override
    public Raid mapRow(ResultSet rs, int rowNum) throws SQLException {
        Raid raid = new Raid();
        raid.setId(rs.getInt("raid_id"));
        raid.setVillageId(rs.getInt("village_id"));
        raid.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
        raid.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
        return raid;
    }
}
