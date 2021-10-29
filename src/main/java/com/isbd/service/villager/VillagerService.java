package com.isbd.service.villager;

import com.isbd.model.Villager;

import java.util.List;

public interface VillagerService {
    List<Villager> getVillagers();

    Villager getVillager(int id);

    List<Villager> getVillagersOfVillage(int villageId);
}
