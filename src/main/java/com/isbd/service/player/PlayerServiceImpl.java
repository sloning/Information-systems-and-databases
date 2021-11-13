package com.isbd.service.player;

import com.isbd.Dao.Dao;
import com.isbd.Dao.DealDao;
import com.isbd.Dao.WithdrawalDao;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Deal;
import com.isbd.model.Player;
import com.isbd.model.Withdrawal;
import com.isbd.service.inventory.InventoryService;
import com.isbd.service.offer.OfferService;
import com.isbd.service.village.VillageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.isbd.config.SecurityConfig.getPlayerId;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final Dao<Player> playerDao;
    private final WithdrawalDao withdrawalDAO;
    private final DealDao dealDAO;
    private final InventoryService inventoryService;
    private final VillageService villageService;
    private final OfferService offerService;

    @Override
    public Player getPlayer() {
        final long playerId = getPlayerId();

        return playerDao.get(playerId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Player with id: %d was not found", playerId)));
    }

    @Override
    public void makeNewWithdrawal(int villageId) {
        final long playerId = getPlayerId();

        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setPlayerId(playerId);
        withdrawal.setVillage(villageService.getVillage(villageId));
        withdrawal.setItems(inventoryService.getByPlayerId(playerId));
        withdrawalDAO.save(withdrawal);
    }

    @Override
    public void makeNewDeal(long offerId) {
        final long playerId = getPlayerId();

        Deal deal = new Deal();
        deal.setPlayerId(playerId);
        deal.setOffer(offerService.getOffer(offerId));
        deal.setTime(new Date());
        dealDAO.save(deal);
    }
}
