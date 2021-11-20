package com.isbd.repository;

import com.isbd.model.ReputationLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
@RequiredArgsConstructor
public class ReputationLevelRepositoryImpl implements Repository<ReputationLevel>, ReputationLevelRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReputationLevel> rowMapper;

    @Override
    public Optional<ReputationLevel> get(long id) {
        String sql = "select * from reputation_level where reputation_level_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(rowMapper), id);
    }

    @Override
    public List<ReputationLevel> getAll() {
        String sql = "select * from reputation_level";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int save(ReputationLevel reputationLevel) {
        return jdbcTemplate.update("insert into reputation_level(name, needed_reputation) values(?, ?)",
                reputationLevel.getName(), reputationLevel.getNeededReputationLevel());
    }

    @Override
    public int update(ReputationLevel reputationLevel) {
        return jdbcTemplate.
                update("update reputation_level set name = ?, needed_reputation = ? where reputation_level_id = ?",
                        reputationLevel.getName(), reputationLevel.getNeededReputationLevel(), reputationLevel.getId());
    }

    @Override
    public int delete(ReputationLevel reputationLevel) {
        return jdbcTemplate.update("delete from reputation_level where reputation_level_id = ?",
                reputationLevel.getId());
    }
}
