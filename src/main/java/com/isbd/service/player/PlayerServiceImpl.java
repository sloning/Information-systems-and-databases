package com.isbd.service.player;

import com.isbd.Dao.*;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.exception.KitHaveBeenAlreadyGivenException;
import com.isbd.exception.WrongCredentialsException;
import com.isbd.model.*;
import com.isbd.security.jwt.JwtUser;
import com.isbd.service.inventory.InventoryService;
import com.isbd.service.offer.OfferService;
import com.isbd.service.village.VillageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final Dao<Player> playerDao;
    private final WithdrawalDao withdrawalDAO;
    private final DealDao dealDAO;
    private final InventoryService inventoryService;
    private final VillageService villageService;
    private final OfferService offerService;
    private final KitObtainmentDao kitObtainmentDao;
    private final KitDao kitDao;
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

    @Override
    public List<InventoryItem> obtainKit(int kitId) {
        playerId = getPlayerId();
        List<Kit> kits = kitDao.getAll();
        if (kits.stream().map(Kit::getId).noneMatch(i -> i == kitId))
            throw new EntityNotFoundException(String.format("Kit with id: %d was not found", kitId));

        Optional<ObtainedKit> obtainedKitOptional = kitObtainmentDao.getByPlayerAndKit(playerId, kitId);
        if (obtainedKitOptional.isPresent()) {
            long hours = ChronoUnit.HOURS.between(obtainedKitOptional.get().getLastObtained(), LocalDateTime.now());
            if (hours < 24) {
                throw new KitHaveBeenAlreadyGivenException(String.format("You must wait another %d hours to get this kit",
                        24 - hours));
            }
        }

        kitDao.obtainKit(playerId, kitId);
        return inventoryService.getByPlayerId(playerId);
    }

    @Override
    public List<ObtainedKit> getKits() {
        playerId = getPlayerId();
        return kitObtainmentDao.getByPlayer(playerId);
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
