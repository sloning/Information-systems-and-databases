package com.isbd.service;

import com.isbd.dto.VillagerDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Pageable;
import com.isbd.model.Villager;
import com.isbd.repository.VillagerRepository;
import com.isbd.service.mapper.VillagerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VillagerService {
    private final VillagerRepository villagerRepository;
    private final VillagerMapper villagerMapper;

    public List<Villager> getVillagers(Pageable pageable) {
        return villagerRepository.getAll(pageable);
    }

    public Villager getVillager(int id) {
        return villagerRepository.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Житель с идентификатором %d не найден", id)));
    }

    public List<Villager> getVillagersOfVillage(int villageId, Pageable pageable) {
        return getValidatedVillagers(villageId, pageable);
    }

    private List<Villager> getValidatedVillagers(int villageId, Pageable pageable) {
        List<Villager> villagers = villagerRepository.getVillagersByVillage(villageId, pageable);
        if (villagers.isEmpty() && pageable.isPresent()) {
            throw new EntityNotFoundException(String.format("В деревне %d не существует жителей", villageId));
        }
        return villagers;
    }

    public List<VillagerDto> getVillagersWithExtraData(int villageId, Pageable pageable) {
        List<VillagerDto> villagerDtos = new ArrayList<>();
        List<Villager> villagers = getVillagersOfVillage(villageId, pageable);

        for (Villager villager : villagers) {
            villagerDtos.add(villagerMapper.createFrom(villager));
        }
        return villagerDtos;
    }
}
