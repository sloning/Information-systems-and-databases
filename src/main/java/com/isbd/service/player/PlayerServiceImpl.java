package com.isbd.service.player;

import com.isbd.DAO.DAO;
import com.isbd.DAO.DealDAO;
import com.isbd.DAO.WithdrawalDAO;
import com.isbd.DTO.DealDTO;
import com.isbd.DTO.WithdrawalDTO;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final DAO<Player> playerDAO;
    private final WithdrawalDAO withdrawalDAO;
    private final DealDAO dealDAO;
    private final InventoryService inventoryService;
    private final VillageService villageService;
    private final OfferService offerService;

    @Override
    public Player getPlayer(long id) {
        checkCredentials(id);

        return playerDAO.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Player with id: %d was not found", id)));
    }

    @Override
    public void makeNewWithdrawal(WithdrawalDTO withdrawalDTO) {
        checkCredentials(withdrawalDTO.getPlayerId());

        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setPlayerId(withdrawalDTO.getPlayerId());
        withdrawal.setVillage(villageService.getVillage(withdrawalDTO.getVillageId()));
        withdrawal.setItems(inventoryService.getByPlayerId(withdrawalDTO.getPlayerId()));
        withdrawalDAO.save(withdrawal);
    }

    @Override
    public void makeNewDeal(DealDTO dealDTO) {
        checkCredentials(dealDTO.getPlayerId());

        Deal deal = new Deal();
        deal.setPlayerId(dealDTO.getPlayerId());
        deal.setOffer(offerService.getOffer(dealDTO.getOfferId()));
        deal.setTime(new Date());
        dealDAO.save(deal);
    }

    private void checkCredentials(long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();

        if (jwtUser.getId() != id) throw new WrongCredentialsException("Access blocked");
    }
}
