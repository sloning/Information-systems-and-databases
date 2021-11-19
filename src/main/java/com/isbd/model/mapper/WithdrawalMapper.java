package com.isbd.model.mapper;

import com.isbd.Dao.WithdrawalCompositionDao;
import com.isbd.model.InventoryItem;
import com.isbd.model.Withdrawal;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WithdrawalMapper implements RowMapper<Withdrawal> {
    private final WithdrawalCompositionDao withdrawalCompositionDao;

    @Override
    public Withdrawal mapRow(ResultSet rs, int rowNum) throws SQLException {
        long playerId = rs.getLong("player_id");
        long withdrawalId = rs.getLong("withdrawal_id");
        List<InventoryItem> items = withdrawalCompositionDao.getByWithdrawal(withdrawalId);

        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setId(withdrawalId);
        withdrawal.setVillageId(rs.getInt("village_id"));
        withdrawal.setItems(items);
        withdrawal.setPlayerId(playerId);
        return withdrawal;
    }
}
