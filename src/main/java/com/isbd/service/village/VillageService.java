package com.isbd.service.village;

import com.isbd.dto.VillageDto;
import com.isbd.model.Village;

import java.util.List;

public interface VillageService {
    List<Village> getVillages(int limit, int offset);

    List<VillageDto> getVillagesWithExtraData(int limit, int offset);

    Village getVillage(int id);

    Village getNearestVillage(int xCoordinate, int zCoordinate);
}
