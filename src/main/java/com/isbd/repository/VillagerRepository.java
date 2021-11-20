package com.isbd.repository;

import com.isbd.model.Villager;

import java.util.List;

public interface VillagerRepository extends Repository<Villager> {
    List<Villager> getVillagersByVillage(int villageId);
}
