package com.isbd.DAO;

import com.isbd.model.AppliedEffect;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AppliedEffectDAOImpl implements DAO<AppliedEffect>, AppliedEffectDAO {
    private final RowMapper<AppliedEffect> rowMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<AppliedEffect> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<AppliedEffect> getByPlayer(long playerId) {
        String sql = "select * from applied_effect where player_id = ?";

        return jdbcTemplate.query(sql, rowMapper, playerId);
    }

    @Override
    public List<AppliedEffect> getAll() {
        return null;
    }

    @Override
    public int save(AppliedEffect appliedEffect) {
        return 0;
    }

    @Override
    public int update(AppliedEffect appliedEffect) {
        return 0;
    }

    @Override
    public int delete(AppliedEffect appliedEffect) {
        return 0;
    }
}
