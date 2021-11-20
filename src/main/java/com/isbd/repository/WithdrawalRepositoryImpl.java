package com.isbd.repository;

import com.isbd.model.InventoryItem;
import com.isbd.model.Withdrawal;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WithdrawalRepositoryImpl implements WithdrawalRepository {
    private final JdbcTemplate jdbcTemplate;
    private final WithdrawalCompositionRepository withdrawalCompositionRepository;

    @Override
    public Optional<Withdrawal> get(long id) {
        String sql = "select * from withdrawal where withdrawal_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToWithdrawal), id);
    }

    public List<Withdrawal> getByPlayer(long playerId) {
        String sql = "select * from withdrawal where player_id = ?";

        return jdbcTemplate.query(sql, this::mapRowToWithdrawal, playerId);
    }

    @Override
    public List<Withdrawal> getAll() {
        return null;
    }

    @Override
    public int save(Withdrawal withdrawal) {
        String sql1 = "insert into withdrawal(village_id, player_id) values(?, ?)";

        return jdbcTemplate.update(sql1, withdrawal.getVillageId(), withdrawal.getPlayerId());
    }

    @Override
    public int update(Withdrawal withdrawal) {
        return 0;
    }

    @Override
    public int delete(Withdrawal withdrawal) {
        return 0;
    }

    private Withdrawal mapRowToWithdrawal(ResultSet rs, int rowNum) throws SQLException {
        long playerId = rs.getLong("player_id");
        long withdrawalId = rs.getLong("withdrawal_id");
        List<InventoryItem> items = withdrawalCompositionRepository.getByWithdrawal(withdrawalId);

        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setId(withdrawalId);
        withdrawal.setVillageId(rs.getInt("village_id"));
        withdrawal.setItems(items);
        withdrawal.setPlayerId(playerId);
        return withdrawal;
    }
}
