package com.isbd.DAO.mapper;

import com.isbd.DAO.AppliedEffectDAO;
import com.isbd.DAO.DealDAO;
import com.isbd.DAO.InventoryDAO;
import com.isbd.DAO.WithdrawalDAO;
import com.isbd.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlayerMapper implements RowMapper<Player> {
    private final WithdrawalDAO withdrawalDAO;
    private final DealDAO dealDAO;
    private final AppliedEffectDAO appliedEffectDAO;
    private final InventoryDAO inventoryDAO;

    @Override
    public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
        long playerId = rs.getLong("player_id");
        List<Withdrawal> withdrawals = withdrawalDAO.getByPlayer(playerId);
        List<Deal> deals = dealDAO.getByPlayer(playerId);
        List<AppliedEffect> appliedEffects = appliedEffectDAO.getByPlayer(playerId);
        Inventory inventory = inventoryDAO.get(playerId).get();

        Player player = new Player();
        player.setId(playerId);
        player.setUsername(rs.getString("username"));
        player.setPassword(rs.getString("password"));
        player.setTradingExperience(rs.getInt("trading_experience"));
        player.setWithdrawals(withdrawals);
        player.setDeals(deals);
        player.setAppliedEffects(appliedEffects);
        player.setInventory(inventory);
        return player;
    }
}
