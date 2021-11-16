package com.isbd.Dao;

import com.isbd.model.Raid;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RaidDaoImpl implements RaidDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Raid> rowMapper;

    @Override
    public Optional<Raid> get(long raidId) {
        String sql = "select * from raid where raid_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(rowMapper), raidId);
    }

    @Override
    public List<Raid> getAll() {
        String sql = "select * from raid";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int save(Raid raid) {
        String sql = "insert into raid(village_id, start_time, end_time) values(?, ?, ?)";

        return jdbcTemplate.update(sql, raid.getVillageId(), raid.getStartTime(), raid.getEndTime());
    }

    @Override
    public int update(Raid raid) {
        return 0;
    }

    @Override
    public int delete(Raid raid) {
        String sql = "delete from raid where raid_id = ?";

        return jdbcTemplate.update(sql, raid.getId());
    }
}
