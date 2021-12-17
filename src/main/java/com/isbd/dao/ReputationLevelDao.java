package com.isbd.dao;

import com.isbd.model.ReputationLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReputationLevelDao {
    private final JdbcTemplate jdbcTemplate;

    public Optional<ReputationLevel> get(long id) {
        String sql = "select * from reputation_level where reputation_level_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToReputationLevel), id);
    }

    public List<ReputationLevel> getAll() {
        String sql = "select * from reputation_level";

        return jdbcTemplate.query(sql, this::mapRowToReputationLevel);
    }

    public int save(ReputationLevel reputationLevel) {
        return jdbcTemplate.update("insert into reputation_level(name, needed_reputation) values(?, ?)",
                reputationLevel.getName(), reputationLevel.getNeededReputationLevel());
    }

    public int update(ReputationLevel reputationLevel) {
        return jdbcTemplate.
                update("update reputation_level set name = ?, needed_reputation = ? where reputation_level_id = ?",
                        reputationLevel.getName(), reputationLevel.getNeededReputationLevel(), reputationLevel.getId());
    }

    public int delete(ReputationLevel reputationLevel) {
        return jdbcTemplate.update("delete from reputation_level where reputation_level_id = ?",
                reputationLevel.getId());
    }

    private ReputationLevel mapRowToReputationLevel(ResultSet rs, int rowNum) throws SQLException {
        ReputationLevel reputationLevel = new ReputationLevel();
        reputationLevel.setId(rs.getInt("reputation_level_id"));
        reputationLevel.setName(rs.getString("name"));
        reputationLevel.setNeededReputationLevel(rs.getInt("needed_reputation"));
        return reputationLevel;
    }
}
