package com.isbd.service.villager;

import com.isbd.Dto.VillagerDto;
import com.isbd.model.Villager;

import java.util.List;

public interface VillagerService {
    Villager getVillager(int id);

    List<Villager> getVillagers(int limit, int offset);

    List<Villager> getVillagersOfVillage(int villageId, int limit, int offset);

    List<VillagerDto> getVillagersWithExtraData(int villageId, int limit, int offset);
}
