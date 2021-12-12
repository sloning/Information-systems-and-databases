package com.isbd.service.mapper;

import com.isbd.dto.VillagerDto;
import com.isbd.model.ReputationLevel;
import com.isbd.model.TradingReputation;
import com.isbd.model.Villager;
import com.isbd.security.AuthenticationFacade;
import com.isbd.service.OfferService;
import com.isbd.service.ReputationLevelService;
import com.isbd.service.TradingReputationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VillagerMapper {
    private final ReputationLevelService reputationLevelService;
    private final OfferService offerService;
    private final AuthenticationFacade authenticationFacade;
    private final TradingReputationService tradingReputationService;

    public VillagerDto createFrom(Villager entity) {
        long playerId = authenticationFacade.getPlayerId();
        ReputationLevel currentReputationLevel = reputationLevelService.getReputationLevelByVillagerId(entity.getId());
        TradingReputation tradingReputation = tradingReputationService.
                getTradingReputationByPlayerIdAndVillagerId(playerId, entity.getId());

        VillagerDto dto = new VillagerDto();
        dto.setVillagerId(entity.getId());
        dto.setName(entity.getName());
        dto.setProfessionName(entity.getProfession().getName());
        dto.setOffersAmount(offerService.getAmountOfOffersByVillagerIdAndReputationLevel(entity.getId(),
                currentReputationLevel.getId()));
        dto.setReputationLevelId(currentReputationLevel.getId());
        dto.setReputationLevelName(currentReputationLevel.getName());
        dto.setCurrentReputation(tradingReputation.getReputation());
        dto.setDealsToNextReputationLevel(getDealsToNextReputationLevel(currentReputationLevel, tradingReputation));
        return dto;
    }

    private int getDealsToNextReputationLevel(ReputationLevel currentReputationLevel,
                                              TradingReputation tradingReputation) {
        ReputationLevel nextReputationLevel = reputationLevelService.getNextReputationLevelOrLast(
                currentReputationLevel.getId());
        if (currentReputationLevel.getId() == nextReputationLevel.getId()) {
            return -1;
        } else {
            return nextReputationLevel.getNeededReputationLevel() - tradingReputation.getReputation();
        }
    }
}
