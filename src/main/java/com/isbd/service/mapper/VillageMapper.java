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

    public VillageDto createFrom(Village entity) {
        boolean hasRaid = raidService.getRaids().stream().map(Raid::getVillageId).anyMatch(villageID ->
                villageID == entity.getId());
        VillageDto dto = new VillageDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setXCoordinate(entity.getXCoordinate());
        dto.setZCoordinate(entity.getZCoordinate());
        dto.setBiome(entity.getBiome());
        dto.setHasRaid(hasRaid);
        return dto;
    }
}
