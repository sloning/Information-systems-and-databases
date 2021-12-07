package com.isbd.service;

import com.isbd.dto.VillagerDto;
import com.isbd.exception.EntityNotFoundException;
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

    public List<Villager> getVillagers(int limit, int offset) {
        return villagerRepository.getAll(limit, offset);
    }

    public Villager getVillager(int id) {
        return villagerRepository.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Житель с идентификатором %d не найден", id)));
    }

    public List<Villager> getVillagersOfVillage(int villageId, int limit, int offset) {
        return villagerRepository.getVillagersByVillage(villageId, limit, offset);
    }

    public List<VillagerDto> getVillagersWithExtraData(int villageId, int limit, int offset) {
        List<VillagerDto> villagerDtos = new ArrayList<>();
        List<Villager> villagers = getVillagersOfVillage(villageId, limit, offset);

        for (Villager villager : villagers) {
            villagerDtos.add(villagerMapper.createFrom(villager));
        }
        return villagerDtos;
    }
}
