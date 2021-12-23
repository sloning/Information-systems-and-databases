package com.isbd.service;

import com.isbd.dao.VillagerDao;
import com.isbd.dto.VillagerDto;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Pageable;
import com.isbd.model.Villager;
import com.isbd.service.mapper.VillagerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VillagerService {
    private final VillagerDao villagerDao;
    private final VillagerMapper villagerMapper;

    public Villager getVillager(int id) {
        return villagerDao.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Житель с идентификатором %d не найден", id)));
    }

    public List<Villager> getVillagers(Pageable pageable) {
        List<Villager> villagers = villagerDao.getAll(pageable);
        if (villagers.isEmpty() && pageable.isPresent()) {
            throw new EntityNotFoundException("Не найдено ни одного жителя");
        }
        return villagers;
    }

    public List<Villager> getVillagersOfVillage(int villageId, Pageable pageable) {
        List<Villager> villagers = villagerDao.getVillagersByVillage(villageId, pageable);
        if (villagers.isEmpty() && pageable.isPresent()) {
            throw new EntityNotFoundException(String.format("В деревне %d не существует жителей", villageId));
        }
        return villagers;
    }

    public VillagerDto getVillagerWithExtraData(int villagerId) {
        return villagerMapper.createFrom(getVillager(villagerId));
    }

    public List<VillagerDto> getVillagersWithExtraData(int villageId, Pageable pageable) {
        return getVillagersOfVillage(villageId, pageable).stream().map(villagerMapper::createFrom)
                .collect(Collectors.toList());
    }
}
