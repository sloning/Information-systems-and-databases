package com.isbd.repository;

import com.isbd.model.Raid;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RaidRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Raid> get(long raidId) {
        String sql = "select * from raid where raid_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToRaid), raidId);
    }

    public List<Raid> getAll() {
        String sql = "select * from raid";

        return jdbcTemplate.query(sql, this::mapRowToRaid);
    }

    public Optional<Raid> getByVillageId(int villageId) {
        String sql = "select * from raid where village_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToRaid), villageId);
    }

    public int save(Raid raid) {
        String sql = "insert into raid(village_id, start_time, end_time) values(?, ?, ?) on conflict do nothing";

        return jdbcTemplate.update(sql, raid.getVillageId(), raid.getStartTime(), raid.getEndTime());
    }

    public int delete(Raid raid) {
        String sql = "delete from raid where raid_id = ?";

        return jdbcTemplate.update(sql, raid.getId());
    }

    private Raid mapRowToRaid(ResultSet rs, int rowNum) throws SQLException {
        Raid raid = new Raid();
        raid.setId(rs.getInt("raid_id"));
        raid.setVillageId(rs.getInt("village_id"));
        raid.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
        raid.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
        return raid;
    }
}
