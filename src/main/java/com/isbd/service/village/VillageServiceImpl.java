package com.isbd.service.village;

import com.isbd.Dao.Dao;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.Village;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VillageServiceImpl implements VillageService {
    private final Dao<Village> villageDao;

    @Override
    public List<Village> getVillages() {
        return villageDao.getAll();
    }

    @Override
    public Village getVillage(int id) {
        return villageDao.get(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Village with id: %d was not found", id)));
    }

    // TODO test
    @Override
    public Village getNearestVillage(int xCoordinate, int zCoordinate) {
        ArrayList<Village> villages = new ArrayList<>(villageDao.getAll());
        if (villages.isEmpty()) throw new EntityNotFoundException("There are no villages");
        Village nearestVillage = villages.get(0);
        int current_distance = Math.abs(xCoordinate - nearestVillage.getXCoordinate()) +
                Math.abs(zCoordinate - nearestVillage.getZCoordinate());
        for (Village village : villages) {
            int distance = Math.abs(xCoordinate - village.getXCoordinate()) +
                    Math.abs(zCoordinate - village.getZCoordinate());
            if (distance < current_distance) {
                nearestVillage = village;
            }
        }
        return nearestVillage;
    }
}
