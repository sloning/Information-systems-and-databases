package com.isbd.DAO;

import com.isbd.model.Withdrawal;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WithdrawalDAOImpl implements DAO<Withdrawal>, WithdrawalDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Withdrawal> rowMapper;

    @Override
    public Optional<Withdrawal> get(long id) {
        String sql = "select * from withdrawal where withdrawal_id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    public List<Withdrawal> getByPlayer(long playerId) {
        String sql = "select * from withdrawal where player_id = ?";

        return jdbcTemplate.query(sql, rowMapper, playerId);
    }

    @Override
    public List<Withdrawal> getAll() {
        return null;
    }

    @Override
    public int save(Withdrawal withdrawal) {
        String sql1 = "insert into withdrawal(village_id, player_id) values(?, ?)";

        return jdbcTemplate.update(sql1, withdrawal.getVillage().getId(), withdrawal.getPlayerId());
    }

    @Override
    public int update(Withdrawal withdrawal) {
        return 0;
    }

    @Override
    public int delete(Withdrawal withdrawal) {
        return 0;
    }
}