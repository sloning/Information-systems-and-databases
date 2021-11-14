package com.isbd.service.player;

import com.isbd.Dao.Dao;
import com.isbd.Dao.DealDao;
import com.isbd.Dao.WithdrawalDao;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.exception.WrongCredentialsException;
import com.isbd.model.Deal;
import com.isbd.model.Player;
import com.isbd.model.Withdrawal;
import com.isbd.security.jwt.JwtUser;
import com.isbd.service.inventory.InventoryService;
import com.isbd.service.offer.OfferService;
import com.isbd.service.village.VillageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final Dao<Player> playerDao;
    private final WithdrawalDao withdrawalDAO;
    private final DealDao dealDAO;
    private final InventoryService inventoryService;
    private final VillageService villageService;
    private final OfferService offerService;
    private long playerId;

    @Override
    public Player getPlayer() {
        playerId = getPlayerId();
        return playerDao.get(playerId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Player with id: %d was not found", playerId)));
    }

    @Override
    public void makeNewWithdrawal(int villageId) {
        playerId = getPlayerId();
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setPlayerId(playerId);
        withdrawal.setVillage(villageService.getVillage(villageId));
        withdrawal.setItems(inventoryService.getByPlayerId(playerId));
        withdrawalDAO.save(withdrawal);
    }

    @Override
    public void makeNewDeal(long offerId) {
        playerId = getPlayerId();
        Deal deal = new Deal();
        deal.setPlayerId(playerId);
        deal.setOffer(offerService.getOffer(offerId));
        deal.setTime(new Date());
        dealDAO.save(deal);
    }

    private long getPlayerId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            JwtUser jwtUser = (JwtUser) auth.getPrincipal();
            return jwtUser.getId();
        }
        throw new WrongCredentialsException("Access blocked");
    }
}
