package com.isbd.service.mapper;

import com.isbd.dto.VillageDto;
import com.isbd.model.Raid;
import com.isbd.model.Village;
import com.isbd.service.RaidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VillageMapper {
    private final RaidService raidService;

    public VillageDto createFrom(Village entity) {
        Optional<Raid> raid = raidService.getRaidByVillageId(entity.getId());

        VillageDto dto = new VillageDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setXCoordinate(entity.getXCoordinate());
        dto.setZCoordinate(entity.getZCoordinate());
        dto.setBiome(entity.getBiome());
        dto.setHasRaid(raid.isPresent());
        raid.ifPresent(value -> dto.setRaidId(value.getId()));
        return dto;
    }
}
