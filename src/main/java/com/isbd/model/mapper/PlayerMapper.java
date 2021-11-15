package com.isbd.model.mapper;

import com.isbd.Dao.AppliedEffectDao;
import com.isbd.Dao.DealDao;
import com.isbd.Dao.KitObtainmentDao;
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
    private final DealDao dealDAO;
    private final AppliedEffectDao appliedEffectDAO;
    private final InventoryService inventoryService;
    private final KitObtainmentDao kitObtainmentDao;

    @Override
    public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
        long playerId = rs.getLong("player_id");
        List<Withdrawal> withdrawals = withdrawalService.getByPlayer(playerId);
        List<Deal> deals = dealDAO.getByPlayer(playerId);
        List<AppliedEffect> appliedEffects = appliedEffectDAO.getByPlayer(playerId);
        List<InventoryItem> items = inventoryService.getByPlayerId(playerId);
        List<ObtainedKit> lastObtainedKits = kitObtainmentDao.getByPlayer(playerId);

        Player player = new Player();
        player.setId(playerId);
        player.setUsername(rs.getString("username"));
        player.setPassword(rs.getString("password"));
        player.setTradingExperience(rs.getInt("trading_experience"));
        player.setWithdrawals(withdrawals);
        player.setDeals(deals);
        player.setAppliedEffects(appliedEffects);
        player.setInventory(items);
        player.setLastObtainedKits(lastObtainedKits);
        return player;
    }
}
