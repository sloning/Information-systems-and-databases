package com.isbd.Dao;

import com.isbd.model.Villager;

import java.util.List;

public interface VillagerDao extends Dao<Villager> {
    List<Villager> getVillagersByVillage(int villageId);
}
