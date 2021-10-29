package com.isbd.service.village;

import com.isbd.model.Village;

import java.util.List;

public interface VillageService {
    List<Village> getVillages();

    Village getVillage(int id);

    Village getNearestVillage(int xCoordinate, int zCoordinate);
}
