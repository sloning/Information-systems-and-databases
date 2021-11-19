package com.isbd.service.villager;

import com.isbd.Dao.ReputationLevelDao;
import com.isbd.Dao.TradingReputationDao;
import com.isbd.Dao.VillagerDao;
import com.isbd.Dto.VillagerDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.ReputationLevel;
import com.isbd.model.TradingReputation;
import com.isbd.model.Villager;
import com.isbd.security.AuthenticationFacade;
import com.isbd.service.offer.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VillagerServiceImpl implements VillagerService {
    private final VillagerDao villagerDAO;
    private final OfferService offerService;
    private final ReputationLevelDao reputationLevelDao;
    private final AuthenticationFacade authenticationFacade;
    private final TradingReputationDao tradingReputationDao;

    @Override
    public List<Villager> getVillagers(int limit, int offset) {
        return villagerDAO.getAll();
    }

    @Override
    public Villager getVillager(int id) {
        return villagerDAO.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Villager with id: %d was not found", id)));
    }

    @Override
    public List<Villager> getVillagersOfVillage(int villageId, int limit, int offset) {
        List<Villager> villagers = villagerDAO.getVillagersByVillage(villageId);
        if (villagers.isEmpty()) throw new EntityNotFoundException(
                String.format("There are no villagers in the village with id: %d", villageId));
        return villagers;
    }

    @Override
    public List<VillagerDto> getVillagersWithExtraData(int villageId, int limit, int offset) {
        List<VillagerDto> villagerDtos = new ArrayList<>();
        List<Villager> villagers = getVillagersOfVillage(villageId, Integer.MAX_VALUE, 0);
        long playerId = authenticationFacade.getPlayerId();
        List<ReputationLevel> reputationLevels = reputationLevelDao.getAll();
        if (reputationLevels.isEmpty()) throw new EntityNotFoundException("No reputation levels. Contact admins.");

        for (Villager villager : villagers) {
            villagerDtos.add(convertToDto(villager, playerId, reputationLevels));
        }
        return villagerDtos;
    }

    private VillagerDto convertToDto(Villager villager, long playerId, List<ReputationLevel> reputationLevels) {
        Optional<TradingReputation> optionalTradingReputation = tradingReputationDao.getByPlayerAndVillager(playerId,
                villager.getId());
        TradingReputation tradingReputation;
        if (optionalTradingReputation.isEmpty()) {
            TradingReputation newTradingReputation = new TradingReputation();
            newTradingReputation.setReputation(0);
            newTradingReputation.setPlayerId(playerId);
            newTradingReputation.setVillagerId(villager.getId());
            tradingReputationDao.save(newTradingReputation);
            tradingReputation = newTradingReputation;
        } else {
            tradingReputation = optionalTradingReputation.get();
        }

        ReputationLevel reputationLevel = reputationLevels.get(0);
        for (ReputationLevel rl : reputationLevels)
            if (tradingReputation.getReputation() >= rl.getNeededReputationLevel())
                reputationLevel = rl;

        VillagerDto villagerDto = new VillagerDto();
        villagerDto.setVillagerId(villager.getId());
        villagerDto.setName(villager.getName());
        villagerDto.setProfessionName(villager.getProfession().getName());
        villagerDto.setOffersAmount(offerService.getAmountOfOffersByVillagerIdAndReputationLevel(villager.getId(),
                reputationLevel.getId()));
        villagerDto.setReputationLevelId(reputationLevel.getId());
        villagerDto.setReputationLevel(reputationLevel.getName());

        return villagerDto;
    }
}
