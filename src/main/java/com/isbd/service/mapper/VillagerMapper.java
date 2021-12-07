package com.isbd.service.mapper;

import com.isbd.dto.VillagerDto;
import com.isbd.model.ReputationLevel;
import com.isbd.model.Villager;
import com.isbd.service.OfferService;
import com.isbd.service.ReputationLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VillagerMapper {
    private final ReputationLevelService reputationLevelService;
    private final OfferService offerService;

    public VillagerDto createFrom(Villager entity) {
        VillagerDto dto = new VillagerDto();
        ReputationLevel reputationLevel = reputationLevelService.getReputationLevelByVillagerId(entity.getId());

        dto.setVillagerId(entity.getId());
        dto.setName(entity.getName());
        dto.setProfessionName(entity.getProfession().getName());
        dto.setOffersAmount(offerService.getAmountOfOffersByVillagerIdAndReputationLevel(entity.getId(),
                reputationLevel.getId()));
        dto.setReputationLevelId(reputationLevel.getId());
        dto.setReputationLevel(reputationLevel.getName());

        return dto;
    }
}
