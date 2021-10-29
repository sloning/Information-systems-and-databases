package com.isbd.DAO;

import com.isbd.model.Villager;

import java.util.List;

public interface VillagerDAO extends DAO<Villager> {
    List<Villager> getVillagersByVillage(int villageId);
}
