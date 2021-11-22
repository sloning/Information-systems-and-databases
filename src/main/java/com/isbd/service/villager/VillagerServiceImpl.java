package com.isbd.service.villager;

import com.isbd.dto.VillagerDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.ReputationLevel;
import com.isbd.model.TradingReputation;
import com.isbd.model.Villager;
import com.isbd.repository.ReputationLevelRepository;
import com.isbd.repository.TradingReputationRepository;
import com.isbd.repository.VillagerRepository;
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
    private final VillagerRepository villagerRepository;
    private final OfferService offerService;
    private final ReputationLevelRepository reputationLevelRepository;
    private final AuthenticationFacade authenticationFacade;
    private final TradingReputationRepository tradingReputationRepository;

    @Override
    public List<Villager> getVillagers(int limit, int offset) {
        return villagerRepository.getAll();
    }

    @Override
    public Villager getVillager(int id) {
        return villagerRepository.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Житель с идентификатором %d не найден", id)));
    }

    @Override
    public List<Villager> getVillagersOfVillage(int villageId, int limit, int offset) {
        List<Villager> villagers = villagerRepository.getVillagersByVillage(villageId);
        if (villagers.isEmpty()) throw new EntityNotFoundException(
                String.format("В деревне с идентификатором %d нет жителей", villageId));
        return villagers;
    }

    @Override
    public List<VillagerDto> getVillagersWithExtraData(int villageId, int limit, int offset) {
        List<VillagerDto> villagerDtos = new ArrayList<>();
        List<Villager> villagers = getVillagersOfVillage(villageId, Integer.MAX_VALUE, 0);
        long playerId = authenticationFacade.getPlayerId();
        List<ReputationLevel> reputationLevels = reputationLevelRepository.getAll();
        if (reputationLevels.isEmpty()) throw new EntityNotFoundException("Уровни репутации не существуют");

        for (Villager villager : villagers) {
            villagerDtos.add(convertToDto(villager, playerId, reputationLevels));
        }
        return villagerDtos;
    }

    private VillagerDto convertToDto(Villager villager, long playerId, List<ReputationLevel> reputationLevels) {
        Optional<TradingReputation> optionalTradingReputation = tradingReputationRepository.getByPlayerAndVillager(playerId,
                villager.getId());
        TradingReputation tradingReputation;
        if (optionalTradingReputation.isEmpty()) {
            TradingReputation newTradingReputation = new TradingReputation();
            newTradingReputation.setReputation(0);
            newTradingReputation.setPlayerId(playerId);
            newTradingReputation.setVillagerId(villager.getId());
            tradingReputationRepository.save(newTradingReputation);
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
