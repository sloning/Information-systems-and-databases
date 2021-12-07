package com.isbd.service.mapper;

import com.isbd.dto.VillageDto;
import com.isbd.model.Raid;
import com.isbd.model.Village;
import com.isbd.service.RaidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VillageMapper {
    private final RaidService raidService;

    public VillageDto createFrom(Village village) {
        boolean hasRaid = raidService.getRaids().stream().map(Raid::getVillageId).anyMatch(villageID ->
                villageID == village.getId());
        VillageDto villageDto = new VillageDto();
        villageDto.setId(village.getId());
        villageDto.setName(village.getName());
        villageDto.setXCoordinate(village.getXCoordinate());
        villageDto.setZCoordinate(village.getZCoordinate());
        villageDto.setHasRaid(hasRaid);
        return villageDto;
    }
}
