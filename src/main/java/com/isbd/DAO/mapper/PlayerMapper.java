package com.isbd.DAO.mapper;

import com.isbd.DAO.AppliedEffectDAO;
import com.isbd.DAO.DealDAO;
import com.isbd.model.*;
import com.isbd.service.inventory.InventoryService;
import com.isbd.service.withdrawal.WithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlayerMapper implements RowMapper<Player> {
    private final WithdrawalService withdrawalService;
    private final DealDAO dealDAO;
    private final AppliedEffectDAO appliedEffectDAO;
    private final InventoryService inventoryService;

    @Override
    public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
        long playerId = rs.getLong("player_id");
        List<Withdrawal> withdrawals = withdrawalService.getByPlayer(playerId);
        List<Deal> deals = dealDAO.getByPlayer(playerId);
        List<AppliedEffect> appliedEffects = appliedEffectDAO.getByPlayer(playerId);
        List<InventoryItem> items = inventoryService.getByPlayerId(playerId);

        Player player = new Player();
        player.setId(playerId);
        player.setUsername(rs.getString("username"));
        player.setPassword(rs.getString("password"));
        player.setTradingExperience(rs.getInt("trading_experience"));
        player.setWithdrawals(withdrawals);
        player.setDeals(deals);
        player.setAppliedEffects(appliedEffects);
        player.setInventory(items);
        return player;
    }
}
