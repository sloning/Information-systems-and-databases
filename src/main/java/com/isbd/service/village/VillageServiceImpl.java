package com.isbd.service.village;

import com.isbd.dto.VillageDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Raid;
import com.isbd.model.Village;
import com.isbd.repository.VillageRepository;
import com.isbd.service.raid.RaidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VillageServiceImpl implements VillageService {
    private final VillageRepository villageRepository;
    private final RaidService raidService;

    @Override
    public List<Village> getVillages(int limit, int offset) {
        return villageRepository.getAll(limit, offset);
    }

    @Override
    public List<VillageDto> getVillagesWithExtraData(int limit, int offset) {
        return getVillages(limit, offset).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Village getVillage(int id) {
        return villageRepository.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Дервня с идентификатором %d не найдена", id)));
    }

    @Override
    public Village getNearestVillage(int xCoordinate, int zCoordinate) {
        ArrayList<Village> villages = new ArrayList<>(villageRepository.getAll(Integer.MAX_VALUE, 0));
        if (villages.isEmpty()) throw new EntityNotFoundException("Деревень не существует");
        Village nearestVillage = villages.get(0);
        int current_distance = Math.abs(xCoordinate - nearestVillage.getXCoordinate()) +
                Math.abs(zCoordinate - nearestVillage.getZCoordinate());
        for (Village village : villages) {
            int distance = Math.abs(xCoordinate - village.getXCoordinate()) +
                    Math.abs(zCoordinate - village.getZCoordinate());
            if (distance < current_distance) {
                nearestVillage = village;
                current_distance = distance;
            }
        }
        return nearestVillage;
    }

    private VillageDto convertToDto(Village village) {
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
