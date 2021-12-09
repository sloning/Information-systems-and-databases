package com.isbd.service;

import com.isbd.dto.VillageDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Pageable;
import com.isbd.model.Village;
import com.isbd.repository.VillageRepository;
import com.isbd.service.mapper.VillageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VillageService {
    private final VillageRepository villageRepository;
    private final VillageMapper villageMapper;

    public List<Village> getVillages(Pageable pageable) {
        return getValidatedVillages(pageable);
    }

    public List<VillageDto> getVillagesWithExtraData(Pageable pageable) {
        return getVillages(pageable).stream().map(villageMapper::createFrom).collect(Collectors.toList());
    }

    public Village getVillage(int id) {
        return villageRepository.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Дервня с идентификатором %d не найдена", id)));
    }

    public Village getNearestVillage(int xCoordinate, int zCoordinate) {
        List<Village> villages = getValidatedVillages(Pageable.all());
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

    private List<Village> getValidatedVillages(Pageable pageable) {
        List<Village> villages = villageRepository.getAll(pageable);
        if (villages.isEmpty() && pageable.isPresent()) {
            throw new EntityNotFoundException("Деревень не существует");
        }
        return villages;
    }
}
