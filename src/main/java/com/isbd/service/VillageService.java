package com.isbd.service;

import com.isbd.dto.VillageDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Village;
import com.isbd.repository.VillageRepository;
import com.isbd.service.mapper.VillageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VillageService {
    private final VillageRepository villageRepository;
    private final VillageMapper villageMapper;

    public List<Village> getVillages(int limit, int offset) {
        return villageRepository.getAll(limit, offset);
    }

    public List<VillageDto> getVillagesWithExtraData(int limit, int offset) {
        return getVillages(limit, offset).stream().map(villageMapper::createFrom).collect(Collectors.toList());
    }

    public Village getVillage(int id) {
        return villageRepository.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Дервня с идентификатором %d не найдена", id)));
    }

    public Village getNearestVillage(int xCoordinate, int zCoordinate) {
        List<Village> villages = getValidatedVillages();
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

    private List<Village> getValidatedVillages() {
        List<Village> villages = new ArrayList<>(villageRepository.getAll(Integer.MAX_VALUE, 0));
        if (villages.isEmpty()) {
            throw new EntityNotFoundException("Деревень не существует");
        }
        return villages;
    }
}
