package com.isbd.service.villager;

import com.isbd.DAO.VillagerDAO;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Villager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VillagerServiceImpl implements VillagerService {
    private final VillagerDAO villagerDAO;

    @Override
    public List<Villager> getVillagers() {
        return villagerDAO.getAll();
    }

    @Override
    public Villager getVillager(int id) {
        return villagerDAO.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Villager with id: %d was not found", id)));
    }

    @Override
    public List<Villager> getVillagersOfVillage(int villageId) {
        List<Villager> villagers = villagerDAO.getVillagersByVillage(villageId);
        if (villagers.isEmpty()) throw new EntityNotFoundException(
                String.format("There are no villagers in the village with id: %d", villageId));
        return villagers;
    }
}
