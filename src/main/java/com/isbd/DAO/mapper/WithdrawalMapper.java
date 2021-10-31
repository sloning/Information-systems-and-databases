package com.isbd.DAO.mapper;

import com.isbd.DAO.DAO;
import com.isbd.DAO.InventoryDAO;
import com.isbd.model.Inventory;
import com.isbd.model.Village;
import com.isbd.model.Withdrawal;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class WithdrawalMapper implements RowMapper<Withdrawal> {
    private final DAO<Village> villageDAO;
    private final InventoryDAO inventoryDAO;

    @Override
    public Withdrawal mapRow(ResultSet rs, int rowNum) throws SQLException {
        long playerId = rs.getLong("player_id");
        Village village = villageDAO.get(rs.getInt("village_id")).get();
        Inventory inventory = inventoryDAO.get(playerId).get();

        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setId(rs.getInt("withdrawal_id"));
        withdrawal.setVillage(village);
        withdrawal.setInventory(inventory);
        withdrawal.setPlayerId(playerId);
        return withdrawal;
    }
}
